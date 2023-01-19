package com.example.finalemodule2.service;

import com.example.finalemodule2.entity.*;
import com.example.finalemodule2.repository.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceServiceTest {

    private static final int RETAIL_LIMIT = 50000;
    private static final int WHOLESALE_LIMIT = 49999;
    private static final BigDecimal DEVICE_PRICE = new BigDecimal(25000);

    private InvoiceRepository repository;
    private Customer customer;
    private List<Device> devices;

    @BeforeEach
    public void setup() {
        repository = InvoiceRepository.getInstance();
        customer = getCustomerForTest();
        devices = new ArrayList<>();
        devices.add(getDeviceForTest());
        devices.add(getDeviceForTest());
    }

    @Test
    void makeInvoiceRetailPositive() {
        Invoice expected = Invoice.builder()
                .customer(customer)
                .devices(devices)
                .creatingDate(new Date())
                .type(Type.RETAIL)
                .build();
        InvoiceService invoiceService = new InvoiceService(repository);
        Invoice actual = invoiceService.makeInvoice(customer, devices, RETAIL_LIMIT);
        assertEquals(expected, actual);
    }

    @Test
    void makeInvoiceRetailNegative() {
        Invoice expected = Invoice.builder()
                .customer(customer)
                .devices(devices)
                .creatingDate(new Date())
                .type(Type.RETAIL)
                .build();
        InvoiceService invoiceService = new InvoiceService(repository);
        Invoice actual = invoiceService.makeInvoice(customer, devices, WHOLESALE_LIMIT);
        assertNotEquals(expected, actual);
    }

    @Test
    void makeInvoiceWholesalePositive() {
        Invoice expected = Invoice.builder()
                .customer(customer)
                .devices(devices)
                .creatingDate(new Date())
                .type(Type.WHOLESALE)
                .build();
        InvoiceService invoiceService = new InvoiceService(repository);
        Invoice actual = invoiceService.makeInvoice(customer, devices, WHOLESALE_LIMIT);
        assertEquals(expected, actual);
    }

    @Test
    void makeInvoiceWholesaleNegative() {
        Invoice expected = Invoice.builder()
                .customer(customer)
                .devices(devices)
                .creatingDate(new Date())
                .type(Type.WHOLESALE)
                .build();
        InvoiceService invoiceService = new InvoiceService(repository);
        Invoice actual = invoiceService.makeInvoice(customer, devices, RETAIL_LIMIT);
        assertNotEquals(expected, actual);
    }



    private Device getDeviceForTest(){
        return Television.builder()
                .deviceType(DeviceType.TELEVISION)
                .series("Series")
                .screenType("screen type")
                .price(DEVICE_PRICE)
                .diagonal(500)
                .country("country")
                .build();
    }




    private Customer getCustomerForTest(){
        return Customer.builder()
                .age(18)
                .email("some@mail")
                .build();
    }

}