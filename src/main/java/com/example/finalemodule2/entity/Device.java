package com.example.finalemodule2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Device {
    private final String series;
    private final String screenType;
    private final BigDecimal price;
}
