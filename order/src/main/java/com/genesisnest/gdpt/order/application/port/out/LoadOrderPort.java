package com.genesisnest.gdpt.order.application.port.out;

import com.genesisnest.gdpt.order.domain.Order;

import java.util.List;
import java.util.Optional;

public interface LoadOrderPort {

    Optional<Order> findById(Long id);

    List<Order> findAll();

    List<Order> findByStatus(com.genesisnest.gdpt.order.domain.OrderStatus status);
}