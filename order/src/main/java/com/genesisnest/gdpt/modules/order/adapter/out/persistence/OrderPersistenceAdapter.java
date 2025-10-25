package com.genesisnest.gdpt.modules.order.adapter.out.persistence;

import com.genesisnest.gdpt.modules.order.application.port.out.LoadOrderPort;
import com.genesisnest.gdpt.modules.order.application.port.out.SaveOrderPort;
import com.genesisnest.gdpt.modules.order.domain.Order;
import com.genesisnest.gdpt.shared.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements LoadOrderPort, SaveOrderPort {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderMapper orderMapper;

    @Override
    public Optional<Order> findById(Long orderId) {
        return orderJpaRepository.findById(orderId)
                .map(orderMapper::toDomain);
    }

    @Override
    public List<Order> findAll() {
        return orderJpaRepository.findAll().stream()
                .map(orderMapper::toDomain)
                .toList();
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        return orderJpaRepository.findByStatus(status).stream()
                .map(orderMapper::toDomain)
                .toList();
    }

    @Override
    public Order save(Order order) {
        OrderJpaEntity entity = orderMapper.toJpaEntity(order);
        OrderJpaEntity savedEntity = orderJpaRepository.save(entity);
        return orderMapper.toDomain(savedEntity);
    }
}
