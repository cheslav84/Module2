package com.example.finalemodule2;

import com.example.finalemodule2.service.ShopService;

import java.io.*;
import java.net.URL;

public class Main {

    private static final String DEVICES_FILE = "devices_.csv";


    public static void main(String[] args) {

        ShopService shopService = new ShopService();


        shopService.getDevices(DEVICES_FILE);


//        String line = "";
//        String splitBy = ",";
//        try {
//parsing a CSV file into BufferedReader class constructor

//            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/devices_new.csv"));


//            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
//            InputStream input = contextClassLoader.getResourceAsStream(DEVICES_FILE);
////            BufferedReader br = new BufferedReader(new InputStreamReader(input));
//
//
//            URL resource = contextClassLoader.getResource(DEVICES_FILE);
//
//            System.out.println(resource.getPath());
//            System.out.println(resource.getFile());
//
//            FileReader fr = new FileReader(resource.getPath());
//            BufferedReader br = new BufferedReader(fr);
//
//
//            while ((line = br.readLine()) != null) {
////                String[] devises = line.split(splitBy);
//                System.out.println(line);
////                System.out.println(Arrays.toString(devises));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }
}
