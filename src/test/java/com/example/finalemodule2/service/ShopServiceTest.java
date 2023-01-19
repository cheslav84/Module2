package com.example.finalemodule2.service;

import com.example.finalemodule2.entity.*;
import com.example.finalemodule2.util.FileReaderFactory;
import com.example.finalemodule2.util.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShopServiceTest {
    private static final String DEVICES_FILE = "devices_.csv";

    private List<Device> actual;

    private List<Map<String, Object>> data;

    @Mock
    private FileReaderFactory fileReaderFactory;
    @Mock
    private Reader reader;

    @InjectMocks
    private ShopService shopService = new ShopService(DEVICES_FILE);

    @BeforeEach
    public void setup() {
        actual = getDevicesForTest();
        data = getTestDataEmulatingFromFile();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDevicesEquals() throws IOException {
        int numberOfDevicesToGet = 2;
        when(reader.readData(DEVICES_FILE)).thenReturn(data);
        shopService.getDevices(numberOfDevicesToGet);
        List<Device> expected = shopService.getDevices(numberOfDevicesToGet);
        assertEquals(actual, expected);
        assertTrue(expected.size() == 2);
    }

    @Test
    void getDevicesNotEquals() throws IOException {
        int numberOfDevicesToGet = 2;
        when(reader.readData(DEVICES_FILE)).thenReturn(data);
        shopService.getDevices(numberOfDevicesToGet);
        List<Device> expected = shopService.getDevices(numberOfDevicesToGet);
        actual.set(0, getAnotherPhone());
        assertNotEquals(actual, expected);
    }

    @Test
    void getDevicesException() throws IOException {
        int numberOfDevicesToGet = 0;
        Exception exception = assertThrows(IllegalArgumentException.class, () -> shopService.getDevices(numberOfDevicesToGet));
        assertEquals("Number of devices should be from 1 to 5", exception.getMessage());
    }

    private List<Map<String, Object>> getTestDataEmulatingFromFile() {
        List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> telephone = new HashMap<>();
        telephone.put("country", "none");
        telephone.put("series", "iPhone 13 128GB Blue");
        telephone.put("price", "35999");
        telephone.put("screen type", "OLED");
        telephone.put("model", "Apple");
        telephone.put("type", "Telephone");
        telephone.put("diagonal", "none");

        Map<String, Object> television = new HashMap<>();
        television.put("country", "China");
        television.put("series", "Kivi 43 4K UHD Smart TV");
        television.put("price", "13679");
        television.put("screen type", "SMVA");
        television.put("model", "none");
        television.put("type", "Television");
        television.put("diagonal", "43");

        data.add(telephone);
        data.add(television);
        return data;
    }

    private List<Device> getDevicesForTest() {
        List<Device> devices = new ArrayList<>();
        devices.add(getTelephoneForTest());
        devices.add(getTelevisionForTest());
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
                .series("iPhone 13 128GB Blue")
                .screenType("OLED")
                .price(new BigDecimal(15999))
                .model("Apple")
                .build();
    }


}