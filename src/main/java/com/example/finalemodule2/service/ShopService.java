package com.example.finalemodule2.service;

import com.example.finalemodule2.entity.*;
import com.example.finalemodule2.exception.BrokenDeviceException;
import com.example.finalemodule2.util.FileReaderFactory;
import com.example.finalemodule2.util.Reader;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

public class ShopService {

    private static final int MIN_NUMBER_OF_DEVICES = 1;
    private static final int MAX_NUMBER_OF_DEVICES = 5;


    public List<Device> getDevices(String devicesFileName, int numberOfDevices) {
        checkNumberOfDevices(numberOfDevices);
        List<Device> allDevices = getAllDevices(devicesFileName);
        return getSomeFromAllDevices(allDevices, numberOfDevices);
    }

    private List<Device> getSomeFromAllDevices(List<Device> allDevices, int numberOfDevices) {
        Map<Integer, Device> randomDeviceNumbers = new HashMap<>();
        while (randomDeviceNumbers.size() < numberOfDevices){
            int randomDeviceNumber = new Random().nextInt(0, allDevices.size());
            randomDeviceNumbers.put(randomDeviceNumber, allDevices.get(randomDeviceNumber));
        }
        return randomDeviceNumbers.values().stream().toList();
    }

    private List<Device> getAllDevices (String devicesFileName) {
        List<Device> devices = null;
        try {
            FileReaderFactory fileReaderFactory = new FileReaderFactory();
            Reader reader = fileReaderFactory.getReader(devicesFileName);
            List<Map<String, Object>> data = reader.readData(devicesFileName);
            devices = data.stream()
                    .map(d -> {
                        try {
                            return mapDevice.apply(d);
                        } catch (BrokenDeviceException e) {
                            System.err.println(e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
        } catch (IOException e) {
            System.err.println("LOG: ERROR - File not found: " + e);
        }
        return devices;
    }


    public Function<Map<String, Object>, Device> mapDevice = deviceMap -> {
        checkDoesNotContainBroken(deviceMap);
        Device device;
        if (getDeviceType(deviceMap).equals(DeviceType.TELEVISION)) {
            device = mapTelevision(deviceMap);
        } else if (getDeviceType(deviceMap).equals(DeviceType.TELEPHONE)) {
            device = mapTelephone(deviceMap);
        } else {
            throw new IllegalArgumentException();
        }
        return device;
    };

    private void checkDoesNotContainBroken(Map<String, Object> deviceMap) {
        deviceMap.values().stream()
                .filter(s -> ((String) s).isEmpty())
                .findAny()
                .ifPresent(a -> {
                    throw new BrokenDeviceException("LOG: ERROR - Device is broken and wont be displayed: " + deviceMap);
                });
    }

    private Television mapTelevision(Map<String, Object> deviceMap) {
        return Television.builder()
                .deviceType(getDeviceType(deviceMap))
                .series((String) deviceMap.get("series"))
                .screenType((String) deviceMap.get("screen type"))
                .price(new BigDecimal((String) deviceMap.get("price")))
                .diagonal(Integer.parseInt((String) deviceMap.get("diagonal")))
                .country((String) deviceMap.get("country"))
                .build();
    }

    private Telephone mapTelephone(Map<String, Object> deviceMap) {
        return Telephone.builder()
                .deviceType(getDeviceType(deviceMap))
                .series((String) deviceMap.get("series"))
                .screenType((String) deviceMap.get("screen type"))
                .price(new BigDecimal((String) deviceMap.get("price")))
                .model((String) deviceMap.get("model"))
                .build();
    }

    private DeviceType getDeviceType(Map<String, Object> deviceMap){
        return DeviceType.valueOf(((String) deviceMap.get("type")).toUpperCase());
    }

    private void checkNumberOfDevices(int numberOfDevices) {
        if (numberOfDevices < MIN_NUMBER_OF_DEVICES ||
                numberOfDevices > MAX_NUMBER_OF_DEVICES) {
            throw new IllegalArgumentException("Number of devices should be from "
                    + MIN_NUMBER_OF_DEVICES + " to " + MAX_NUMBER_OF_DEVICES);
        }
    }



}
