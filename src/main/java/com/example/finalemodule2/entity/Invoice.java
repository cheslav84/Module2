package com.example.finalemodule2.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class Invoice {
    private Customer customer;
    private List<Device> devices;
    @Setter(AccessLevel.NONE)
    private Type type;

    @Builder
    public Invoice(Customer customer, double limit, List<Device> devices) {
        this.customer = customer;
        this.devices = devices;
        this.type = setType(limit, devices);
    }

    private Type setType(double limit, List<Device> devices) {
        BigDecimal sum = devices.stream()
                .map(device -> device.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return (sum.compareTo(new BigDecimal(limit)) > 0) ? Type.WHOLESALE : Type.RETAIL;
    }

}
