package com.genesisnest.gdpt.order.application.port.in;

import com.genesisnest.gdpt.common.Address;
import com.genesisnest.gdpt.common.Money;
import com.genesisnest.gdpt.order.domain.Order;

public interface CreateOrderUseCase {

    Order createOrder(CreateOrderCommand command);

    record CreateOrderCommand(
            String customerName,
            String customerPhone,
            Address deliveryAddress,
            Money totalAmount,
            String description) {
    }
}