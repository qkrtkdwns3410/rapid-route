package com.genesisnest.gdpt.modules.order.domain;

import com.genesisnest.gdpt.shared.Address;
import com.genesisnest.gdpt.shared.Money;
import com.genesisnest.gdpt.shared.OrderStatus;

import java.time.LocalDateTime;

public class Order {

    private Long id;
    private String customerName;
    private String customerPhone;
    private Address deliveryAddress;
    private Money totalAmount;
    private OrderStatus status;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Order(String customerName, String customerPhone, Address deliveryAddress,
            Money totalAmount, String description) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.deliveryAddress = deliveryAddress;
        this.totalAmount = totalAmount;
        this.description = description;
        this.status = OrderStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Private constructor for reconstruction from persistence layer
    private Order() {
        // For reconstruction only
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
        this.updatedAt = LocalDateTime.now();
    }

    public void startDelivery() {
        if (this.status != OrderStatus.CONFIRMED) {
            throw new IllegalStateException("확정된 주문만 배송을 시작할 수 있습니다.");
        }
        this.status = OrderStatus.IN_PROGRESS;
        this.updatedAt = LocalDateTime.now();
    }

    public void complete() {
        if (this.status != OrderStatus.IN_PROGRESS) {
            throw new IllegalStateException("진행중인 주문만 완료할 수 있습니다.");
        }
        this.status = OrderStatus.COMPLETED;
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (this.status == OrderStatus.COMPLETED) {
            throw new IllegalStateException("완료된 주문은 취소할 수 없습니다.");
        }
        this.status = OrderStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
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

    // Getters
    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public Money getTotalAmount() {
        return totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Factory method for persistence layer to reconstruct domain entity
    public static Order reconstruct(Long id, String customerName, String customerPhone,
            Address deliveryAddress, Money totalAmount, String description,
            OrderStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        Order order = new Order();
        order.id = id;
        order.customerName = customerName;
        order.customerPhone = customerPhone;
        order.deliveryAddress = deliveryAddress;
        order.totalAmount = totalAmount;
        order.description = description;
        order.status = status;
        order.createdAt = createdAt;
        order.updatedAt = updatedAt;
        return order;
    }
}
