package com.example.finalemodule2;

import com.example.finalemodule2.entity.Customer;
import com.example.finalemodule2.entity.Device;
import com.example.finalemodule2.entity.DeviceType;
import com.example.finalemodule2.repository.InvoiceRepository;
import com.example.finalemodule2.service.InvoiceService;
import com.example.finalemodule2.service.PersonService;
import com.example.finalemodule2.service.ShopService;
import com.example.finalemodule2.util.InvoiceEmulator;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String DEVICES_FILE = "devices_.csv";
    private static final double LIMIT = 50000;

    public static void main(String[] args) {

        int numberOfDevicesToOrder = 5;

        // INIT SERVICES
        InvoiceRepository repository = InvoiceRepository.getInstance();
        ShopService shopService = new ShopService(DEVICES_FILE);
        PersonService personService = new PersonService();
        InvoiceService invoiceService = new InvoiceService(repository);

        //MAKE ONE INVOICE
        List<Device> devices = shopService.getDevices(numberOfDevicesToOrder);
        Customer customer = personService.getRandomCustomer();
        invoiceService.makeInvoice(customer, devices, LIMIT);

        //EMULATING ANOTHER INVOICES
        int amountInvoicesToEmulate = 14;
        InvoiceEmulator.emulateInvoices(shopService, amountInvoicesToEmulate, LIMIT);

        //STATISTICS (MADE IN REPOSITORY, AS YOU CAN GET THAT FROM REAL DATABASE BY SQL QUERIES)
        long televisionAmount = repository.getAmountOfSoldDevices(DeviceType.TELEVISION);
        System.out.println("Amount of sold televisions: " + televisionAmount);
        long telephonesAmount = repository.getAmountOfSoldDevices(DeviceType.TELEPHONE);
        System.out.println("Amount of sold televisions: " + telephonesAmount);
//        System.out.println("Minimal price of order: " + repository.lowestOrderPrice() + " UAH.");

        //todo тут була ідея отримати кілька покупців якщо вони матимуть однакову мінімальну ціну.
        // поки що не вдалось. Буду радий якщо покажете як це зробити на стрімах.
        Map<Customer, BigDecimal> theLowestPriceAndUser = repository.getUserWithLowestInvoice();
        Customer customerWithLowestPrice = getCustomer(theLowestPriceAndUser);
        System.out.println("The the customers with " + customerWithLowestPrice + " made the ");

        System.out.println("Sum of all orders: " + repository.getSumOfAllOrders() + " UAH.");
        System.out.println("Amount of retail invoices: " + repository.getAmountOfRetailInvoices());
        System.out.println("\n Invoices that have one type of device: " + repository.getOneTypeDeviceInvoices() + "\n");
        System.out.println("\n First three invoices in repository: " + repository.getFirstThreeInvoices() + "\n");

        int customerAge = 18;
        System.out.println("\n First three invoices in repository: " + repository.invoicesWithUserAgeLessThan(customerAge) + "\n");
        System.out.println("\n Sorted invoices: " + repository.sortInvoices() + "\n");


    }

    private static Customer getCustomer(Map<Customer, BigDecimal> theLowestPriceAndUser){
        Iterator<Customer> iterator = theLowestPriceAndUser.keySet().iterator();
        if(iterator.hasNext()){
            return iterator.next();
        }
        throw new IllegalArgumentException("The lowest price can't be obtained.");
    }

}
