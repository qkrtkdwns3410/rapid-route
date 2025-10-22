package com.genesisnest.gdpt.domain.aggregate;

import com.genesisnest.gdpt.domain.audit.BaseEntity;
import com.genesisnest.gdpt.domain.vo.Money;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "agency_quotes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class AgencyQuote extends BaseEntity {
    
    @Id
    private String id;
    
    @Column(name = "order_id", nullable = false)
    private String orderId;
    
    @Column(name = "agency_id", nullable = false)
    private String agencyId;
    
    @Column(name = "agency_name", nullable = false)
    private String agencyName;
    
    @Embedded
    private Money basePrice;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "fuel_surcharge_amount")),
        @AttributeOverride(name = "currency", column = @Column(name = "fuel_surcharge_currency"))
    })
    private Money fuelSurcharge;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "distance_fee_amount")),
        @AttributeOverride(name = "currency", column = @Column(name = "distance_fee_currency"))
    })
    private Money distanceFee;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "weight_fee_amount")),
        @AttributeOverride(name = "currency", column = @Column(name = "weight_fee_currency"))
    })
    private Money weightFee;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "total_price_amount")),
        @AttributeOverride(name = "currency", column = @Column(name = "total_price_currency"))
    })
    private Money totalPrice;
    
    @Column(name = "estimated_delivery_time")
    private LocalDateTime estimatedDeliveryTime;
    
    @lombok.Setter
    @Column(name = "estimated_distance")
    private BigDecimal estimatedDistance; // km
    
    @lombok.Setter
    @Column(name = "estimated_duration")
    private Integer estimatedDuration; // minutes
    
    @lombok.Setter
    @Column(name = "vehicle_type")
    private String vehicleType;
    
    @lombok.Setter
    @Column(name = "service_level")
    private String serviceLevel; // STANDARD, EXPRESS, SAME_DAY
    
    @lombok.Setter
    @Column(name = "quote_valid_until")
    private LocalDateTime quoteValidUntil;
    
    @Column(name = "special_conditions")
    private String specialConditions;
    
    // 정적 팩토리 메서드
    public static AgencyQuote create(String id, String orderId, String agencyId, String agencyName,
                                    Money basePrice, Money fuelSurcharge, Money distanceFee,
                                    Money weightFee, LocalDateTime estimatedDeliveryTime) {
        Money totalPrice = basePrice
                .add(fuelSurcharge)
                .add(distanceFee)
                .add(weightFee);
        
        return AgencyQuote.builder()
                .id(id)
                .orderId(orderId)
                .agencyId(agencyId)
                .agencyName(agencyName)
                .basePrice(basePrice)
                .fuelSurcharge(fuelSurcharge)
                .distanceFee(distanceFee)
                .weightFee(weightFee)
                .totalPrice(totalPrice)
                .estimatedDeliveryTime(estimatedDeliveryTime)
                .build();
    }
    
    public static AgencyQuote createWithServiceDetails(String id, String orderId, String agencyId, String agencyName,
                                                      Money basePrice, Money fuelSurcharge, Money distanceFee,
                                                      Money weightFee, LocalDateTime estimatedDeliveryTime,
                                                      String vehicleType, String serviceLevel) {
        Money totalPrice = basePrice
                .add(fuelSurcharge)
                .add(distanceFee)
                .add(weightFee);
        
        return AgencyQuote.builder()
                .id(id)
                .orderId(orderId)
                .agencyId(agencyId)
                .agencyName(agencyName)
                .basePrice(basePrice)
                .fuelSurcharge(fuelSurcharge)
                .distanceFee(distanceFee)
                .weightFee(weightFee)
                .totalPrice(totalPrice)
                .estimatedDeliveryTime(estimatedDeliveryTime)
                .vehicleType(vehicleType)
                .serviceLevel(serviceLevel)
                .build();
    }
    
    public static AgencyQuote createWithValidity(String id, String orderId, String agencyId, String agencyName,
                                                Money basePrice, Money fuelSurcharge, Money distanceFee,
                                                Money weightFee, LocalDateTime estimatedDeliveryTime,
                                                LocalDateTime quoteValidUntil) {
        Money totalPrice = basePrice
                .add(fuelSurcharge)
                .add(distanceFee)
                .add(weightFee);
        
        return AgencyQuote.builder()
                .id(id)
                .orderId(orderId)
                .agencyId(agencyId)
                .agencyName(agencyName)
                .basePrice(basePrice)
                .fuelSurcharge(fuelSurcharge)
                .distanceFee(distanceFee)
                .weightFee(weightFee)
                .totalPrice(totalPrice)
                .estimatedDeliveryTime(estimatedDeliveryTime)
                .quoteValidUntil(quoteValidUntil)
                .build();
    }
    
    // 도메인 로직
    
    public void updateEstimatedDeliveryTime(LocalDateTime newTime) {
        this.estimatedDeliveryTime = newTime;
    }
    
    public void setSpecialConditions(String conditions) {
        this.specialConditions = conditions;
    }
    
    public boolean isExpired() {
        return quoteValidUntil != null && LocalDateTime.now().isAfter(quoteValidUntil);
    }
    
    public boolean isCheaperThan(AgencyQuote other) {
        return this.totalPrice.isGreaterThan(other.totalPrice);
    }
    
    public Money getPriceDifference(AgencyQuote other) {
        if (this.totalPrice.isGreaterThan(other.totalPrice)) {
            return this.totalPrice.subtract(other.totalPrice);
        } else {
            return other.totalPrice.subtract(this.totalPrice);
        }
    }
}
