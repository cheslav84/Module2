package com.example.finalemodule2.service;

import com.example.finalemodule2.entity.Device;
import com.example.finalemodule2.entity.DeviceType;
import com.example.finalemodule2.entity.Telephone;
import com.example.finalemodule2.entity.Television;
import com.example.finalemodule2.util.FileReaderFactory;
import com.example.finalemodule2.util.Reader;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ShopService {


    public List<Device> getDevices (String devicesFileName) {

        List<Device> devices = null;
        try {
            FileReaderFactory fileReaderFactory = new FileReaderFactory();
            Reader reader = fileReaderFactory.getReader(devicesFileName);
            List<Map<String, Object>> data = reader.readData(devicesFileName);
            devices = data.stream()
                    .map(s -> mapDevice.apply(s)).toList();
        } catch (IOException e) {
            System.err.println("File error or I/O error: " + e);
        }
        return devices;
    }


    public Function<Map<String, Object>, Device> mapDevice = map -> {

        System.out.println(map);

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



}
