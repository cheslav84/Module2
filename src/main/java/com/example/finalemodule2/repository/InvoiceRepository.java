package com.example.finalemodule2.repository;

import com.example.finalemodule2.entity.*;
import com.example.finalemodule2.util.comparators.invoice.InvoiceDevicesAmountComparator;
import com.example.finalemodule2.util.comparators.invoice.InvoicePriceSumComparator;
import com.example.finalemodule2.util.comparators.invoice.InvoiceUserAgeComparator;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class InvoiceRepository {

    private static List<Invoice> invoices = new ArrayList<>();

    private static InvoiceRepository instance;

    private InvoiceRepository() {
    }

    public static InvoiceRepository getInstance() {
        if (instance == null) {
            return new InvoiceRepository();
        } else {
            return instance;
        }
    }

    public void saveInvoice(Invoice invoice) {
        invoices.add(invoice);
    }

    public long amountOfSoldDevices(DeviceType deviceType){
        return invoices.stream()
                        .map(Invoice::getDevices)
                        .flatMap(Collection::stream)
                        .filter(device -> device.getDeviceType().equals(deviceType))
                .count();
    }

    public BigDecimal lowestOrderPrice(){
        return invoices.stream()
                .min(new InvoicePriceSumComparator())
                .stream()
                .peek(invoice -> System.out.println(invoice.getCustomer()))
                .flatMap(invoice -> invoice.getDevices().stream())
                .map(Device::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public Map<Customer, BigDecimal> getUserWithLowestInvoice() {
        return invoices.stream()
                .min(new InvoicePriceSumComparator())
                .stream()
                .collect(Collectors.toMap(
                        Invoice::getCustomer,
                        inv -> inv.getDevices().stream()
                                .map(Device::getPrice)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                ));
    }

    public BigDecimal sumOfAllOrders(){
        return invoices.stream()
                .map(Invoice::getDevices)
                .flatMap(Collection::stream)
                .map(Device::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public long amountOfRetailInvoices(){
        return invoices.stream()
                .filter(invoice -> invoice.getType().equals(Type.RETAIL))
                .count();
    }

    public List<Invoice> oneTypeDeviceInvoices(){
        return invoices.stream()
                .filter(devices -> 1 == devices.getDevices().stream()
                        .map(Device::getDeviceType)
                        .distinct()
                        .toArray().length
                )
                .collect(Collectors.toList());
    }

    public List<Invoice> firstThreeInvoices() {
        return invoices.stream()
                .limit(3)
                .collect(Collectors.toList());
    }

    public List<Invoice> invoicesWithUserAgeLessThan(int age) {
        return invoices.stream()
                .filter(invoice -> invoice.getCustomer().getAge() < age)
                .peek(inv -> inv.setType(Type.LOW_AGE))
                .collect(Collectors.toList());
    }

    public List<Invoice> sortInvoices() {
        return invoices.stream().sorted(
                new InvoiceUserAgeComparator()
                        .thenComparing(new InvoiceDevicesAmountComparator())
                        .thenComparing(new InvoicePriceSumComparator())
        ).collect(Collectors.toList());
    }

}
