package com.example.finalemodule2.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Device {
    private final DeviceType deviceType;
    private final String series;
    private final String screenType;
    private final BigDecimal price;

    @Override
    public String toString() {
        return "Series: " + series
                + "; ScreenType: " + screenType
                + "; Price: " + price + " UAH" + "\n";
    }

}
