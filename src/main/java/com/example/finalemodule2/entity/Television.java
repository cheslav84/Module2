package com.example.finalemodule2.entity;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Television extends Device {
    private final int diagonal;
    private final String country;

    @Builder
    public Television(String series, String screenType, BigDecimal price, int diagonal, String country) {
        super(series, screenType, price);
        this.diagonal = diagonal;
        this.country = country;
    }



}
