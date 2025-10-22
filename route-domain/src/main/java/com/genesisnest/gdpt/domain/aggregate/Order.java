package com.genesisnest.gdpt.domain.aggregate;

import com.genesisnest.gdpt.domain.audit.BaseEntity;
import com.genesisnest.gdpt.domain.vo.Address;
import com.genesisnest.gdpt.domain.vo.Money;
import com.genesisnest.gdpt.domain.vo.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Order extends BaseEntity {
    
    @Id
    private String id;
    
    @Column(name = "customer_id", nullable = false)
    private String customerId;
    
    @Column(name = "customer_name", nullable = false)
    private String customerName;
    
    @Column(name = "customer_phone")
    private String customerPhone;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street", column = @Column(name = "pickup_street")),
        @AttributeOverride(name = "city", column = @Column(name = "pickup_city")),
        @AttributeOverride(name = "state", column = @Column(name = "pickup_state")),
        @AttributeOverride(name = "postalCode", column = @Column(name = "pickup_postal_code")),
        @AttributeOverride(name = "country", column = @Column(name = "pickup_country"))
    })
    private Address pickupAddress;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street", column = @Column(name = "delivery_street")),
        @AttributeOverride(name = "city", column = @Column(name = "delivery_city")),
        @AttributeOverride(name = "state", column = @Column(name = "delivery_state")),
        @AttributeOverride(name = "postalCode", column = @Column(name = "delivery_postal_code")),
        @AttributeOverride(name = "country", column = @Column(name = "delivery_country"))
    })
    private Address deliveryAddress;
    
    @Column(name = "item_description")
    private String itemDescription;
    
    @Column(name = "item_weight")
    private BigDecimal itemWeight; // kg
    
    @Column(name = "item_volume")
    private BigDecimal itemVolume; // m³
    
    @Embedded
    private Money itemValue;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;
    
    @Column(name = "preferred_pickup_time")
    private LocalDateTime preferredPickupTime;
    
    @Column(name = "preferred_delivery_time")
    private LocalDateTime preferredDeliveryTime;
    
    @Column(name = "special_instructions")
    private String specialInstructions;
    
    @Column(name = "estimated_distance")
    private BigDecimal estimatedDistance; // km
    
    // 정적 팩토리 메서드
    public static Order create(String id, String customerId, String customerName,
                              Address pickupAddress, Address deliveryAddress,
                              String itemDescription, BigDecimal itemWeight, Money itemValue) {
        return Order.builder()
                .id(id)
                .customerId(customerId)
                .customerName(customerName)
                .pickupAddress(pickupAddress)
                .deliveryAddress(deliveryAddress)
                .itemDescription(itemDescription)
                .itemWeight(itemWeight)
                .itemValue(itemValue)
                .status(OrderStatus.PENDING)
                .build();
    }
    
    public static Order createWithPhone(String id, String customerId, String customerName, String customerPhone,
                                       Address pickupAddress, Address deliveryAddress,
                                       String itemDescription, BigDecimal itemWeight, Money itemValue) {
        return Order.builder()
                .id(id)
                .customerId(customerId)
                .customerName(customerName)
                .customerPhone(customerPhone)
                .pickupAddress(pickupAddress)
                .deliveryAddress(deliveryAddress)
                .itemDescription(itemDescription)
                .itemWeight(itemWeight)
                .itemValue(itemValue)
                .status(OrderStatus.PENDING)
                .build();
    }
    
    public static Order createWithTimes(String id, String customerId, String customerName,
                                       Address pickupAddress, Address deliveryAddress,
                                       String itemDescription, BigDecimal itemWeight, Money itemValue,
                                       LocalDateTime preferredPickupTime, LocalDateTime preferredDeliveryTime) {
        return Order.builder()
                .id(id)
                .customerId(customerId)
                .customerName(customerName)
                .pickupAddress(pickupAddress)
                .deliveryAddress(deliveryAddress)
                .itemDescription(itemDescription)
                .itemWeight(itemWeight)
                .itemValue(itemValue)
                .preferredPickupTime(preferredPickupTime)
                .preferredDeliveryTime(preferredDeliveryTime)
                .status(OrderStatus.PENDING)
                .build();
    }
}
