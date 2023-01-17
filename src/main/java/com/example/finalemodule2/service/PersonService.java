package com.example.finalemodule2.service;

import com.example.finalemodule2.entity.Customer;

import java.util.Random;

public class PersonService {

    public Customer getRandomCustomer () {
        return Customer.builder()
                .age(getRandomAge())
                .email(getRandomEmail())
                .build();
    }

    private String getRandomEmail() {
        String email = "";
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        while (email.length() < 5) {
            int character = (int) (Math.random() * 26);
            email += alphabet.substring(character, character + 1);
            email += Integer.valueOf((int) (Math.random() * 99)).toString();
            email += "@mail.com";
        }
        return email;
    }

    private int getRandomAge() {
            return new Random().nextInt(18, 100);
    }


}
