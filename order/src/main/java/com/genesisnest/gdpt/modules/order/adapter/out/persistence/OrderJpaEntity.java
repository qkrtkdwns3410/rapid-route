package com.genesisnest.gdpt.modules.order.adapter.out.persistence;

import com.genesisnest.gdpt.shared.Address;
import com.genesisnest.gdpt.shared.BaseEntity;
import com.genesisnest.gdpt.shared.Money;
import com.genesisnest.gdpt.shared.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderJpaEntity extends BaseEntity {

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "customer_phone", nullable = false)
    private String customerPhone;

    @Embedded
    private Address deliveryAddress;

    @Embedded
    private Money totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "description")
    private String description;

    public OrderJpaEntity(String customerName, String customerPhone, Address deliveryAddress,
            Money totalAmount, String description, OrderStatus status) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.deliveryAddress = deliveryAddress;
        this.totalAmount = totalAmount;
        this.description = description;
        this.status = status;
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

    // Factory method for reconstruction from domain entity
    public static OrderJpaEntity reconstruct(Long id, String customerName, String customerPhone,
            Address deliveryAddress, Money totalAmount, String description, OrderStatus status,
            java.time.LocalDateTime createdAt, java.time.LocalDateTime updatedAt) {
        OrderJpaEntity entity = new OrderJpaEntity();
        entity.setId(id);
        entity.customerName = customerName;
        entity.customerPhone = customerPhone;
        entity.deliveryAddress = deliveryAddress;
        entity.totalAmount = totalAmount;
        entity.description = description;
        entity.status = status;
        entity.setCreatedAt(createdAt);
        entity.setUpdatedAt(updatedAt);
        return entity;
    }
}
