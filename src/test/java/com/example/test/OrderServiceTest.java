package com.example.test;

import com.example.test.dto.Item;
import com.example.test.dto.Order;
import com.example.test.model.OrderRepresentation;
import com.example.test.repository.OrderRepresentationRepository;
import com.example.test.service.OrderService;
import com.example.test.validator.OrderValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class OrderServiceTest {


    private OrderService orderService;

    private OrderValidator orderValidator;
    private OrderRepresentationRepository orderRepresentationRepository;

    @BeforeEach
    public void setUp() {

        this.orderValidator = mock(OrderValidator.class);
        this.orderRepresentationRepository = mock(OrderRepresentationRepository.class);

        doNothing().when(orderValidator).validateNewOrder(any());
        given(orderRepresentationRepository.save(any())).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        this.orderService = new OrderService(orderRepresentationRepository, orderValidator);

    }

    @DisplayName("Save order with success")
    @Test
    public void testSaveOrderWithSuccess() {

        Order order = createSimpleOrder();

        OrderRepresentation orderRepresentation = this.orderService.save(order);

        assertTrue(orderRepresentation.getId() != null);
        assertEquals("test", orderRepresentation.getClientName());
        assertEquals("test_address", orderRepresentation.getDeliveryAddress());
        assertEquals("item1", orderRepresentation.getItems().get(0).getName());
        assertEquals(Integer.valueOf(1), orderRepresentation.getItems().get(0).getAmount());
        assertEquals(BigDecimal.TEN, orderRepresentation.getItems().get(0).getValue());

        ArgumentCaptor<Order> argOrder = ArgumentCaptor.forClass(Order.class);
        ArgumentCaptor<OrderRepresentation> argOrderRep = ArgumentCaptor.forClass(OrderRepresentation.class);
        verify(this.orderRepresentationRepository, times(1)).save(argOrderRep.capture());
        verify(this.orderValidator, times(1)).validateNewOrder(argOrder.capture());
        verifyNoMoreInteractions(this.orderRepresentationRepository);
        verifyNoMoreInteractions(this.orderValidator);

        assertEquals("test", argOrder.getValue().getClientName());
        assertEquals("test_address", argOrder.getValue().getDeliveryAddress());
        assertEquals("item1", argOrder.getValue().getItems().get(0).getName());
        assertEquals(Integer.valueOf(1), argOrder.getValue().getItems().get(0).getAmount());
        assertEquals(BigDecimal.TEN, argOrder.getValue().getItems().get(0).getValue());

        assertTrue(argOrderRep.getValue().getId() != null);
        assertEquals("test", argOrderRep.getValue().getClientName());
        assertEquals("test_address", argOrderRep.getValue().getDeliveryAddress());
        assertEquals("item1", argOrderRep.getValue().getItems().get(0).getName());
        assertEquals(Integer.valueOf(1), argOrderRep.getValue().getItems().get(0).getAmount());
        assertEquals(BigDecimal.TEN, argOrderRep.getValue().getItems().get(0).getValue());


    }


    private Order createSimpleOrder() {

        return Order.builder()
                .clientName("test")
                .deliveryAddress("test_address")
                .items(List.of(Item.builder()
                        .name("item1")
                        .amount(1)
                        .value(BigDecimal.TEN)
                        .build()))
                .build();
    }


}
