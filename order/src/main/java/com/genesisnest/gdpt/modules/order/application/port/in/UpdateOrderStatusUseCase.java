package com.genesisnest.gdpt.modules.order.application.port.in;

import com.genesisnest.gdpt.modules.order.domain.Order;
import com.genesisnest.gdpt.shared.OrderStatus;

public interface UpdateOrderStatusUseCase {

    Order updateOrderStatus(UpdateOrderStatusCommand command);

    record UpdateOrderStatusCommand(
            Long orderId,
            OrderStatus status) {
    }
}
