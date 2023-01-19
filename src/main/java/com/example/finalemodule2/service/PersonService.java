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
        StringBuilder email = new StringBuilder();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        while (email.length() < 5) {
            int character = (int) (Math.random() * 26);
            email.append(alphabet.charAt(character));
            email.append(Integer.valueOf((int) (Math.random() * 99)).toString());
            email.append("@mail.com");
        }
        return email.toString();
    }

    private int getRandomAge() {
            return new Random().nextInt(14, 80);
    }


}
