package com.example.finalemodule2.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class Invoice {
    private Customer customer;
    private Type type;
    private List<Device> devices;

}
