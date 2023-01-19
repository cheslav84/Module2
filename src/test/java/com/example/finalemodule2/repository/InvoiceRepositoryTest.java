package com.example.finalemodule2.repository;

import com.example.finalemodule2.entity.*;
import com.example.finalemodule2.service.InvoiceService;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;


class InvoiceRepositoryTest {

    private static InvoiceRepository repo;

    @BeforeAll
    public static void setup() {
        repo = InvoiceRepository.getInstance();
    }

    @AfterEach
    public void cleanRepo() {
        repo.clear();
    }

    @Test
    void getInstance() {
        InvoiceRepository sameInstance = InvoiceRepository.getInstance();
        assertSame(repo, sameInstance);
    }

    @Test
    void amountOfSoldDevices() {
        repo.saveInvoice(getInvoiceFourDevicesTwoPhonesTwoTVsRetail());
        repo.saveInvoice(getInvoiceThreeDevicesOnePhoneThoTVRetailAgeFifty());
        repo.saveInvoice(getInvoiceThreeDevicesTwoPhonesOneTVWholesaleAgeFifty());
        long telephonesAmount = repo.getAmountOfSoldDevices(DeviceType.TELEPHONE);
        long televisionsAmount = repo.getAmountOfSoldDevices(DeviceType.TELEVISION);
        assertEquals(5, telephonesAmount);
        assertEquals(5, televisionsAmount);
    }

    @Test
    void getUserWithLowestInvoice() {
        repo.saveInvoice(getInvoiceFourDevicesTwoPhonesTwoTVsRetail());
        repo.saveInvoice(getInvoiceFirstAddedOneDeviseWithLowerPrice());
        repo.saveInvoice(getInvoiceThreeDevicesOnePhoneThoTVRetailAgeFifty());
        Map<Customer, BigDecimal> theLowestPriceAndUser = repo.getUserWithLowestInvoice();
        Customer customerWithLowestPrice = getCustomer(theLowestPriceAndUser);
        BigDecimal lowestOrderPriceInv = theLowestPriceAndUser.get(customerWithLowestPrice);
        BigDecimal expectedPrice = getInvoiceFirstAddedOneDeviseWithLowerPrice()
                .getDevices()
                .get(0)
                .getPrice();
        assertEquals(expectedPrice, lowestOrderPriceInv);
        assertEquals(getCustomerWithAgeEighteen(), customerWithLowestPrice);
    }

    @Test
    void sumOfAllOrders() {
        repo.saveInvoice(getInvoiceFourDevicesTwoPhonesTwoTVsRetail());
        repo.saveInvoice(getInvoiceThreeDevicesOnePhoneThoTVRetailAgeFifty());
        repo.saveInvoice(getInvoiceThreeDevicesTwoPhonesOneTVWholesaleAgeFifty());
        BigDecimal sumAllOrders = repo.getSumOfAllOrders();
        assertEquals(new BigDecimal(204130), sumAllOrders);
    }

    @Test
    void amountOfRetailInvoices() {
        repo.saveInvoice(getInvoiceFourDevicesTwoPhonesTwoTVsRetail());
        repo.saveInvoice(getInvoiceThreeDevicesOnePhoneThoTVRetailAgeFifty());
        repo.saveInvoice(getInvoiceThreeDevicesTwoPhonesOneTVWholesaleAgeFifty());
        long amountRetailInvoices = repo.getAmountOfRetailInvoices();
        assertEquals(2, amountRetailInvoices);
    }

    @Test
    void amountOfRetailInvoicesZero() {
        repo.saveInvoice(getInvoiceThreeDevicesTwoPhonesOneTVWholesaleAgeFifty());
        long amountRetailInvoices = repo.getAmountOfRetailInvoices();
        assertEquals(0, amountRetailInvoices);
    }

    @Test
    void oneTypeDeviceInvoicesTrue() {
        repo.saveInvoice(getInvoiceFirstAddedOneDeviseWithLowerPrice());
        repo.saveInvoice(getInvoiceFourDevicesTwoPhonesTwoTVsRetail());
        repo.saveInvoice(getInvoiceThreeDevicesOnePhoneThoTVRetailAgeFifty());
        repo.saveInvoice(getInvoiceThreeDevicesTwoPhonesOneTVWholesaleAgeFifty());
        repo.saveInvoice(getInvoiceDevicesOneType());
        List<Invoice> oneTypeDevices = repo.getOneTypeDeviceInvoices();
        Predicate<List<Device>> p = d -> d.stream().allMatch(s -> s.getDeviceType().equals(DeviceType.TELEPHONE));
        Predicate<List<Device>> t = d -> d.stream().allMatch(s -> s.getDeviceType().equals(DeviceType.TELEVISION));
        boolean isOneType = oneTypeDevices.stream()
                .map(Invoice::getDevices)
                .allMatch(p.or(t));
        assertTrue(isOneType);
    }

    @Test
    void firstThreeInvoices() {
        repo.saveInvoice(getInvoiceFirstAddedOneDeviseWithLowerPrice());
        repo.saveInvoice(getInvoiceSecondAddedTwoDevicesUnderEighteen());
        repo.saveInvoice(getInvoiceThirdAddedThreeDevicesUnderEighteen());
        repo.saveInvoice(getInvoiceFourDevicesTwoPhonesTwoTVsRetail());
        List<Invoice> invoices = repo.getFirstThreeInvoices();
        assertEquals(getInvoiceFirstAddedOneDeviseWithLowerPrice(), invoices.get(0));
        assertEquals(getInvoiceSecondAddedTwoDevicesUnderEighteen(), invoices.get(1));
        assertEquals(getInvoiceThirdAddedThreeDevicesUnderEighteen(), invoices.get(2));
    }

    @Test
    void invoicesWithUserAgeLessThan() {
        repo.saveInvoice(getInvoiceFirstAddedOneDeviseWithLowerPrice());
        repo.saveInvoice(getInvoiceSecondAddedTwoDevicesUnderEighteen());
        repo.saveInvoice(getInvoiceThirdAddedThreeDevicesUnderEighteen());
        repo.saveInvoice(getInvoiceFourDevicesTwoPhonesTwoTVsRetail());
        int ageOfCustomer = 18;
        List<Invoice> invoices = repo.invoicesWithUserAgeLessThan(ageOfCustomer);
        assertEquals(getCustomerWithAgeUnderEighteenLowerInvoice(), invoices.get(0).getCustomer());
        assertEquals(getCustomerWithAgeUnderEighteenLowerInvoice(), invoices.get(1).getCustomer());
        assertEquals(Type.LOW_AGE, invoices.get(0).getType());
        assertEquals(Type.LOW_AGE, invoices.get(1).getType());
        assertEquals(2, invoices.size());
    }

    @Test
    void sortInvoices() {
        InvoiceService service = new InvoiceService(repo);
        repo.saveInvoice(getInvoiceFirstAddedOneDeviseWithLowerPrice());
        repo.saveInvoice(getInvoiceSecondAddedTwoDevicesUnderEighteen());
        repo.saveInvoice(getInvoiceThirdAddedThreeDevicesUnderEighteen());
        repo.saveInvoice(getInvoiceThreeDevicesOnePhoneThoTVRetailAgeFifty());
        repo.saveInvoice(getInvoiceThreeDevicesTwoPhonesOneTVWholesaleAgeFifty());
        List<Invoice> invoices = repo.sortInvoices();

        assertTrue(invoices.get(0).getCustomer().getAge() >= invoices.get(1).getCustomer().getAge());
        assertTrue(invoices.get(1).getCustomer().getAge() >= invoices.get(2).getCustomer().getAge());
        assertTrue(invoices.get(2).getCustomer().getAge() >= invoices.get(3).getCustomer().getAge());
        assertTrue(invoices.get(3).getCustomer().getAge() >= invoices.get(4).getCustomer().getAge());

        assertTrue(invoices.get(0).getDevices().size() >= invoices.get(1).getDevices().size());
        assertTrue(invoices.get(1).getDevices().size() >= invoices.get(2).getDevices().size());
        assertTrue(invoices.get(3).getDevices().size() >= invoices.get(4).getDevices().size());

        assertTrue(service.getTotalPrice(invoices.get(1)).compareTo(service.getTotalPrice(invoices.get(2))) >=0 );
        assertTrue(service.getTotalPrice(invoices.get(3)).compareTo(service.getTotalPrice(invoices.get(4))) >=0 );
    }




    private Invoice getInvoiceFirstAddedOneDeviseWithLowerPrice() {
        return Invoice.builder()
                .customer(getCustomerWithAgeEighteen())
                .devices(getDevicesOneDeviceAndLowerPrice())
                .creatingDate(new Date())
                .type(Type.RETAIL)
                .build();
    }

    private Invoice getInvoiceSecondAddedTwoDevicesUnderEighteen() {
        return Invoice.builder()
                .customer(getCustomerWithAgeUnderEighteenLowerInvoice())
                .devices(getTwoDevices())
                .creatingDate(new Date())
                .type(Type.RETAIL)
                .build();
    }

    private Invoice getInvoiceThirdAddedThreeDevicesUnderEighteen() {
        return Invoice.builder()
                .customer(getCustomerWithAgeUnderEighteenLowerInvoice())
                .devices(getTwoDevices())
                .creatingDate(new Date())
                .type(Type.RETAIL)
                .build();
    }

    private Invoice getInvoiceFourDevicesTwoPhonesTwoTVsRetail() {
        return Invoice.builder()
                .customer(getCustomerWithAgeEighteen())
                .devices(getFourDevicesTwoPhonesTwoTVs())
                .creatingDate(new Date())
                .type(Type.RETAIL)
                .build();
    }

    private Invoice getInvoiceThreeDevicesOnePhoneThoTVRetailAgeFifty() {
        return Invoice.builder()
                .customer(getCustomerWithAgeFifty())
                .devices(getThreeDevicesOnePhoneTwoTV())
                .creatingDate(new Date())
                .type(Type.RETAIL)
                .build();
    }
    private Invoice getInvoiceThreeDevicesTwoPhonesOneTVWholesaleAgeFifty() {
        return Invoice.builder()
                .customer(getCustomerWithAgeFifty())
                .devices(getThreeDevicesTwoPhonesOneTV())
                .creatingDate(new Date())
                .type(Type.WHOLESALE)
                .build();
    }

    private Invoice getInvoiceDevicesOneType() {
        return Invoice.builder()
                .customer(getCustomerWithAgeEighteen())
                .devices(getDevicesOneType())
                .creatingDate(new Date())
                .type(Type.WHOLESALE)
                .build();
    }



    private List<Device> getDevicesOneDeviceAndLowerPrice() {
        List<Device> devices = new ArrayList<>();
        devices.add(getAnotherPhone());
        return devices;
    }

    private List<Device> getDevicesOneType() {
        List<Device> devices = new ArrayList<>();
        devices.add(getTelephoneForTest());
        devices.add(getAnotherPhone());
        return devices;
    }

    private List<Device> getTwoDevices() {
        List<Device> devices = new ArrayList<>();
        devices.add(getTelevisionForTest());
        devices.add(getAnotherPhone());
        return devices;
    }

    private List<Device> getFourDevicesTwoPhonesTwoTVs() {/////////////////////////////////
        List<Device> devices = new ArrayList<>();
        devices.add(getTelephoneForTest());//35999
        devices.add(getAnotherPhone());//3699
        devices.add(getTelevisionForTest());//13679
        devices.add(getAnotherTelevision());//39999
        return devices;
    }

    private List<Device> getThreeDevicesOnePhoneTwoTV() {
        List<Device> devices = new ArrayList<>();
        devices.add(getAnotherPhone());//3699
        devices.add(getTelevisionForTest());//13679
        devices.add(getAnotherTelevision());//39999
        return devices;
    }

    private List<Device> getThreeDevicesTwoPhonesOneTV() {
        List<Device> devices = new ArrayList<>();
        devices.add(getTelevisionForTest());//13679
        devices.add(getTelephoneForTest());//35999
        devices.add(getAnotherPhone());//3699
        return devices;
    }


    private Device getTelevisionForTest(){
        return Television.builder()
                .deviceType(DeviceType.TELEVISION)
                .series("Kivi 43 4K UHD Smart TV")
                .screenType("SMVA")
                .price(new BigDecimal(13679))
                .diagonal(43)
                .country("China")
                .build();
    }

    private Device getTelephoneForTest(){
        return Telephone.builder()
                .deviceType(DeviceType.TELEPHONE)
                .series("iPhone 13 128GB Blue")
                .screenType("OLED")
                .price(new BigDecimal(35999))
                .model("Apple")
                .build();
    }

    private Device getAnotherPhone(){
        return Telephone.builder()
                .deviceType(DeviceType.TELEPHONE)
                .series("Redmi A1 2/32GB (Light Green)")
                .screenType("IPS")
                .price(new BigDecimal(3699))
                .model("Xiaomi")
                .build();
    }

    private Device getAnotherTelevision(){
        return Television.builder()
                .deviceType(DeviceType.TELEVISION)
                .series("Philips 48OLED707/12")
                .screenType("OLED")
                .price(new BigDecimal(39999))
                .diagonal(48)
                .country("Poland")
                .build();
    }

    private Customer getCustomerWithAgeUnderEighteenLowerInvoice(){
        return Customer.builder()
                .age(17)
                .email("some@mail")
                .build();
    }

    private Customer getCustomerWithAgeEighteen(){
        return Customer.builder()
                .age(18)
                .email("another@mail")
                .build();
    }

    private Customer getCustomerWithAgeFifty(){
        return Customer.builder()
                .age(50)
                .email("again@mail")
                .build();
    }

    private static Customer getCustomer(Map<Customer, BigDecimal> theLowestPriceAndUser){
        Iterator<Customer> iterator = theLowestPriceAndUser.keySet().iterator();
        Customer key = null;
        if(iterator.hasNext()){
            return key = iterator.next();
        }
        throw new IllegalArgumentException("The lowest price can't be obtained.");
    }
}