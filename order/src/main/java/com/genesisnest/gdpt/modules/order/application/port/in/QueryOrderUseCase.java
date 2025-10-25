package com.genesisnest.gdpt.modules.order.application.port.in;

import com.genesisnest.gdpt.modules.order.domain.Order;
import com.genesisnest.gdpt.shared.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface QueryOrderUseCase {

    Optional<Order> findOrderById(Long orderId);

    List<Order> findAllOrders();

    List<Order> findOrdersByStatus(OrderStatus status);
}
