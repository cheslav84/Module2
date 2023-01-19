package com.example.finalemodule2.entity;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
//@EqualsAndHashCode
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        if (customer != null ? !customer.equals(invoice.customer) : invoice.customer != null) return false;
        if (devices != null ? !devices.equals(invoice.devices) : invoice.devices != null) return false;
        if (type != invoice.type) return false;
        return creatingDate != null ? creatingDate.toString().equals(invoice.creatingDate.toString()) : invoice.creatingDate == null;
    }

    @Override
    public int hashCode() {
        int result = customer != null ? customer.hashCode() : 0;
        result = 31 * result + (devices != null ? devices.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (creatingDate != null ? creatingDate.hashCode() : 0);
        return result;
    }
}
