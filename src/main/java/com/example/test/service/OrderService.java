package com.example.test.service;

import com.example.test.dto.Order;
import com.example.test.exception.ValidationException;
import com.example.test.model.OrderRepresentation;
import com.example.test.repository.OrderRepresentationRepository;
import com.example.test.validator.OrderValidator;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class OrderService {

    private final OrderRepresentationRepository repository;
    private final OrderValidator validator;

    public OrderRepresentation save(Order order) {

        validator.validateNewOrder(order);

        OrderRepresentation orderRepresentation = order.toOrderRepresentation(UUID.randomUUID().toString());

        OrderRepresentation alreadyHasOrder = repository.findByClientName(order.getClientName());

        if (alreadyHasOrder != null) {
            throw new ValidationException("Client already has a order.");
        }

        return repository.save(orderRepresentation);

    }

}
