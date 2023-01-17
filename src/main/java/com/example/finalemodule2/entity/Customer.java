package com.example.finalemodule2.entity;

import lombok.*;

@Getter
@ToString
public class Customer {
    @Getter(AccessLevel.NONE)
    private static long count = 0;
    private long id = 0;
    private String email;
    int age;

    @Builder
    public Customer(String email, int age) {
        this.id = ++count;
        this.email = email;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Email: " + email
                + "; Age: " + age;
    }
}
