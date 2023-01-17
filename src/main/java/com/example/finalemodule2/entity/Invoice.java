package com.example.finalemodule2.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Invoice {
    private Customer customer;
    private List<Device> devices;
    private Type type;
    @Setter(AccessLevel.NONE)
    private Date creatingDate;

    @Builder
    public Invoice(Customer customer, List<Device> devices, Date creatingDate, Type type) {
        this.customer = customer;
        this.devices = devices;
        this.creatingDate = creatingDate;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Invoice:" + "\n"
                + "  Creation date: " + creatingDate + "\n"
                + "  Customer - " + customer + "\n"
                + "  Type: " + type.toString().toLowerCase() + "\n"
                + "  Devices: " + toString(devices);
    }

    private String toString(List<Device> devices) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (Device device : devices) {
            sb.append("    ");
            sb.append(device);
        }
        return sb.toString();
    }
}
