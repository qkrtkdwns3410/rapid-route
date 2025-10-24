package com.genesisnest.gdpt.order.adapter.out.persistence;

import com.genesisnest.gdpt.order.application.port.out.LoadOrderPort;
import com.genesisnest.gdpt.order.application.port.out.SaveOrderPort;
import com.genesisnest.gdpt.order.domain.Order;
import com.genesisnest.gdpt.order.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements SaveOrderPort, LoadOrderPort {

    private final OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }
}