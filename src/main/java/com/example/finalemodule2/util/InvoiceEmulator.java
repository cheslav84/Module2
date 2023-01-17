package com.example.finalemodule2.util;

import com.example.finalemodule2.entity.Customer;
import com.example.finalemodule2.entity.Device;
import com.example.finalemodule2.repository.InvoiceRepository;
import com.example.finalemodule2.service.InvoiceService;
import com.example.finalemodule2.service.PersonService;
import com.example.finalemodule2.service.ShopService;

import java.util.List;
import java.util.Random;

public class InvoiceEmulator {


    public static void emulateInvoices(int amount, String devicesFileName, double limit) {
        ShopService shopService = new ShopService();
        PersonService personService = new PersonService();
        InvoiceService invoiceService = new InvoiceService(InvoiceRepository.getInstance());

        for (int i = 0; i < amount; i++) {
            List<Device> devices = shopService.
                    getDevices(devicesFileName, new Random().nextInt(1, 5));
            Customer customer = personService.getRandomCustomer();
            invoiceService.makeInvoice(customer, devices, limit);

        }
    }
}
