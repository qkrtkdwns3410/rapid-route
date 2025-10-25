package com.genesisnest.gdpt.modules.order.application.port.out;

import com.genesisnest.gdpt.modules.order.domain.Order;
import com.genesisnest.gdpt.shared.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface LoadOrderPort {

    Optional<Order> findById(Long orderId);

    List<Order> findAll();

    List<Order> findByStatus(OrderStatus status);
}
