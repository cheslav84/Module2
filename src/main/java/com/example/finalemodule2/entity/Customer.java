package com.example.finalemodule2.entity;

import lombok.*;

@Getter
@EqualsAndHashCode(exclude = {"id"})
public class Customer {
    @Getter(AccessLevel.NONE)
    private static long count;
    private final long id;
    private final String email;
    private final int age;

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
