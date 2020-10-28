package com.example.test.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class Item {

    private final String name;
    private final Integer amount;
    private final BigDecimal value;
}
