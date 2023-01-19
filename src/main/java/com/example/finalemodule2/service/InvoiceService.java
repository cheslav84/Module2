package com.example.finalemodule2.service;

import com.example.finalemodule2.entity.Customer;
import com.example.finalemodule2.entity.Device;
import com.example.finalemodule2.entity.Invoice;
import com.example.finalemodule2.entity.Type;
import com.example.finalemodule2.repository.InvoiceRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice makeInvoice (Customer customer, List<Device> devices, double limit) {
        checkData(customer, limit);
            Invoice invoice = Invoice.builder()
                    .customer(customer)
                    .devices(devices)
                    .creatingDate(new Date())
                    .type(getType(limit, devices))
                    .build();
        invoiceRepository.saveInvoice(invoice);
        logInvoice(invoice);
        return invoice;
    }

    private void checkData(Customer customer, double limit) {
        if (customer == null || limit <= 0) {
            throw new IllegalArgumentException();
        }
    }

    private void logInvoice(Invoice invoice) {
        System.out.println(invoice);
    }

    private Type getType(double limit, List<Device> devices) {
        BigDecimal sum = devices.stream()
                .map(Device::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return (sum.compareTo(new BigDecimal(limit)) > 0) ? Type.WHOLESALE : Type.RETAIL;
    }



}
