package com.example.finalemodule2.entity;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Telephone extends Device {
    private final String model;

    @Builder
    public Telephone(DeviceType deviceType, String series, String screenType, BigDecimal price, String model) {
        super(deviceType, series, screenType, price);
        this.model = model;
    }

    @Override
    public String toString() {
        return super.getDeviceType() + " - "
                + "Model: " + model
                +  "; " + super.toString();
    }
}
