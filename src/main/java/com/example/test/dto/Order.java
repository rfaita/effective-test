package com.example.test.dto;


import com.example.test.model.OrderRepresentation;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Order {

    private final String clientName;
    private final String deliveryAddress;
    private final List<Item> items;

    public OrderRepresentation toOrderRepresentation(String id) {
        return OrderRepresentation.builder()
                .id(id)
                .clientName(this.clientName)
                .deliveryAddress(this.deliveryAddress)
                .items(this.items)
                .build();
    }

}
