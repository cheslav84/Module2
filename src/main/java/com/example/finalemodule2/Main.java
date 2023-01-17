package com.example.finalemodule2;

import com.example.finalemodule2.entity.Device;
import com.example.finalemodule2.service.PersonService;
import com.example.finalemodule2.service.ShopService;

import java.util.List;

public class Main {

    private static final String DEVICES_FILE = "devices_.csv";
    private static final double LIMIT = 50000;



    public static void main(String[] args) {

        int numberOfDevices = 5;
        ShopService shopService = new ShopService();
        List<Device> deviceList = shopService.getAllDevices(DEVICES_FILE, numberOfDevices);
        deviceList.forEach(System.out::println);


        PersonService personService = new PersonService();
//        System.out.println(personService.getRandomCustomer());




    }
}
