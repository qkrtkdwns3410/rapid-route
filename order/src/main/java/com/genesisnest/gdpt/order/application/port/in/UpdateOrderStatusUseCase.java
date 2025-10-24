package com.genesisnest.gdpt.order.application.port.in;

import com.genesisnest.gdpt.order.domain.Order;
import com.genesisnest.gdpt.order.domain.OrderStatus;

public interface UpdateOrderStatusUseCase {

    Order updateOrderStatus(Long orderId, OrderStatus newStatus);

    record UpdateOrderStatusCommand(
            Long orderId,
            OrderStatus newStatus) {
    }
}