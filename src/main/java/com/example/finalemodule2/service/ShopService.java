package com.example.finalemodule2.service;

import com.example.finalemodule2.entity.Device;
import com.example.finalemodule2.entity.DeviceType;
import com.example.finalemodule2.entity.Telephone;
import com.example.finalemodule2.entity.Television;
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


    public List<Device> getAllDevices (String devicesFileName, int numberOfDevices) {
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

    public List<Device> getAllDevices (String devicesFileName) {
        List<Device> devices = null;
        try {
            FileReaderFactory fileReaderFactory = new FileReaderFactory();
            Reader reader = fileReaderFactory.getReader(devicesFileName);
            List<Map<String, Object>> data = reader.readData(devicesFileName);
            devices = data.stream()
                    .map(s -> {
                        try {
                            return mapDevice.apply(s);
                        } catch (BrokenDeviceException e) {
                            System.err.println(e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
        } catch (IOException e) {
            System.err.println("File error or I/O error: " + e);
        }
        return devices;
    }


    public Function<Map<String, Object>, Device> mapDevice = map -> {
        checkDoesNotContainBroken(map);
        DeviceType deviceType = DeviceType.valueOf(((String) map.get("type")).toUpperCase());
        Device device;
        if (deviceType.equals(DeviceType.TELEVISION)) {
            device = mapTelevision(map);
        } else if (deviceType.equals(DeviceType.TELEPHONE)) {
            device = mapTelephone(map);
        } else {
            throw new IllegalArgumentException();
        }
        return device;
    };

    private void checkDoesNotContainBroken(Map<String, Object> map) {
        map.values().stream()
                .filter(s -> ((String) s).isEmpty())
                .findAny()
                .ifPresent(a -> {
                    throw new BrokenDeviceException("This device is broken: " + map);
                });
    }

    private Television mapTelevision(Map<String, Object> map) {
        return Television.builder()
                .series((String) map.get("series"))
                .screenType((String) map.get("screen type"))
                .price(new BigDecimal((String) map.get("price")))
                .diagonal(Integer.parseInt((String) map.get("diagonal")))
                .country((String) map.get("country"))
                .build();
    }

    private Telephone mapTelephone(Map<String, Object> map) {
        return Telephone.builder()
                .series((String) map.get("series"))
                .screenType((String) map.get("screen type"))
                .price(new BigDecimal((String) map.get("price")))
                .model((String) map.get("model"))
                .build();
    }

    private void checkNumberOfDevices(int numberOfDevices) {
        if (numberOfDevices < MIN_NUMBER_OF_DEVICES ||
                numberOfDevices > MAX_NUMBER_OF_DEVICES) {
            throw new IllegalArgumentException("Number of devices should be from "
                    + MIN_NUMBER_OF_DEVICES + " to " + MAX_NUMBER_OF_DEVICES);
        }
    }

}
