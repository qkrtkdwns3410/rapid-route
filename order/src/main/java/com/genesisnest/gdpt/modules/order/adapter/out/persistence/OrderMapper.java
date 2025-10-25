package com.genesisnest.gdpt.modules.order.adapter.out.persistence;

import com.genesisnest.gdpt.modules.order.domain.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderJpaEntity toJpaEntity(Order order) {
        return OrderJpaEntity.reconstruct(
                order.getId(),
                order.getCustomerName(),
                order.getCustomerPhone(),
                order.getDeliveryAddress(),
                order.getTotalAmount(),
                order.getDescription(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getUpdatedAt());
    }

    public Order toDomain(OrderJpaEntity entity) {
        return Order.reconstruct(
                entity.getId(),
                entity.getCustomerName(),
                entity.getCustomerPhone(),
                entity.getDeliveryAddress(),
                entity.getTotalAmount(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}
