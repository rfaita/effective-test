package com.example.test.validator;

import com.example.test.dto.Item;
import com.example.test.dto.Order;
import com.example.test.exception.ValidationException;

public class OrderValidator {

    public void validateNewOrder(Order order) {

        if (order.getClientName() == null || order.getClientName().isEmpty()) {
            throw new ValidationException("Client name is empty.");
        }

        if (order.getDeliveryAddress() == null || order.getDeliveryAddress().isEmpty()) {
            throw new ValidationException("Delivery address is empty.");
        }

        for (Item item : order.getItems()) {
            if (item.getAmount() == null || item.getAmount() <= 0) {
                throw new ValidationException("Invalid amount on item ".concat(item.getName()));
            }
        }

    }

}
