package com.genesisnest.gdpt.order.domain;

import com.genesisnest.gdpt.common.Address;
import com.genesisnest.gdpt.common.BaseEntity;
import com.genesisnest.gdpt.common.Money;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

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

    private Order(String customerName, String customerPhone, Address deliveryAddress,
            Money totalAmount, String description) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.deliveryAddress = deliveryAddress;
        this.totalAmount = totalAmount;
        this.description = description;
        this.status = OrderStatus.PENDING;
    }

    public static Order create(String customerName, String customerPhone, Address deliveryAddress,
            Money totalAmount, String description) {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("고객명은 필수입니다.");
        }
        if (customerPhone == null || customerPhone.trim().isEmpty()) {
            throw new IllegalArgumentException("고객 전화번호는 필수입니다.");
        }
        if (deliveryAddress == null) {
            throw new IllegalArgumentException("배송 주소는 필수입니다.");
        }
        if (totalAmount == null) {
            throw new IllegalArgumentException("총 금액은 필수입니다.");
        }

        return new Order(customerName, customerPhone, deliveryAddress, totalAmount, description);
    }

    public void confirm() {
        if (this.status != OrderStatus.PENDING) {
            throw new IllegalStateException("대기중인 주문만 확정할 수 있습니다.");
        }
        this.status = OrderStatus.CONFIRMED;
    }

    public void startDelivery() {
        if (this.status != OrderStatus.CONFIRMED) {
            throw new IllegalStateException("확정된 주문만 배송을 시작할 수 있습니다.");
        }
        this.status = OrderStatus.IN_PROGRESS;
    }

    public void complete() {
        if (this.status != OrderStatus.IN_PROGRESS) {
            throw new IllegalStateException("진행중인 주문만 완료할 수 있습니다.");
        }
        this.status = OrderStatus.COMPLETED;
    }

    public void cancel() {
        if (this.status == OrderStatus.COMPLETED) {
            throw new IllegalStateException("완료된 주문은 취소할 수 없습니다.");
        }
        this.status = OrderStatus.CANCELLED;
    }

    public void updateStatus(OrderStatus newStatus) {
        switch (newStatus) {
            case CONFIRMED -> confirm();
            case IN_PROGRESS -> startDelivery();
            case COMPLETED -> complete();
            case CANCELLED -> cancel();
            default -> throw new IllegalArgumentException("유효하지 않은 상태 변경입니다.");
        }
    }
}