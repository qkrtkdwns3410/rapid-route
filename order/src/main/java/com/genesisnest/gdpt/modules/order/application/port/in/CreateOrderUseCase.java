package com.genesisnest.gdpt.modules.order.application.port.in;

import com.genesisnest.gdpt.modules.order.domain.Order;

import java.math.BigDecimal;

public interface CreateOrderUseCase {

    Order createOrder(CreateOrderCommand command);

    record CreateOrderCommand(
            String customerName,
            String customerPhone,
            String street,
            String city,
            String zipCode,
            BigDecimal amount,
            String description) {
    }
}
