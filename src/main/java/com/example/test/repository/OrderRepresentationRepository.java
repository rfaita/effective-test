package com.example.test.repository;

import com.example.test.model.OrderRepresentation;

public interface OrderRepresentationRepository {

    OrderRepresentation save(OrderRepresentation orderRepresentation);

    OrderRepresentation findByClientName(String clientName);
}
