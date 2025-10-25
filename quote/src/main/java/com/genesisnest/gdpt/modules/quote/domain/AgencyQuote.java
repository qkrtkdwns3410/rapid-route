package com.genesisnest.gdpt.modules.quote.domain;

import com.genesisnest.gdpt.shared.Money;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AgencyQuote {

    private Long id;
    private String agencyName;
    private Money estimatedFee;
    private Integer estimatedTimeMinutes;
    private Long orderId;
    private Boolean isSelected;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private AgencyQuote(String agencyName, Money estimatedFee, Integer estimatedTimeMinutes, Long orderId) {
        this.agencyName = agencyName;
        this.estimatedFee = estimatedFee;
        this.estimatedTimeMinutes = estimatedTimeMinutes;
        this.orderId = orderId;
        this.isSelected = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Private constructor for reconstruction from persistence layer
    private AgencyQuote() {
        // For reconstruction only
    }

    public static AgencyQuote create(String agencyName, BigDecimal estimatedFee, Integer estimatedTimeMinutes,
            Long orderId) {
        if (agencyName == null || agencyName.trim().isEmpty()) {
            throw new IllegalArgumentException("대행사명은 필수입니다.");
        }
        if (estimatedFee == null) {
            throw new IllegalArgumentException("예상 배달비는 필수입니다.");
        }
        if (estimatedTimeMinutes == null || estimatedTimeMinutes <= 0) {
            throw new IllegalArgumentException("예상 소요시간은 0보다 커야 합니다.");
        }
        if (orderId == null) {
            throw new IllegalArgumentException("주문 ID는 필수입니다.");
        }

        return new AgencyQuote(agencyName, new Money(estimatedFee), estimatedTimeMinutes, orderId);
    }

    public void select() {
        this.isSelected = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void unselect() {
        this.isSelected = false;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isCheaperThan(AgencyQuote other) {
        return this.estimatedFee.isLessThan(other.estimatedFee);
    }

    public boolean isFasterThan(AgencyQuote other) {
        return this.estimatedTimeMinutes < other.estimatedTimeMinutes;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public Money getEstimatedFee() {
        return estimatedFee;
    }

    public Integer getEstimatedTimeMinutes() {
        return estimatedTimeMinutes;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Factory method for persistence layer to reconstruct domain entity
    public static AgencyQuote reconstruct(Long id, String agencyName, Money estimatedFee,
            Integer estimatedTimeMinutes, Long orderId, Boolean isSelected,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        AgencyQuote quote = new AgencyQuote();
        quote.id = id;
        quote.agencyName = agencyName;
        quote.estimatedFee = estimatedFee;
        quote.estimatedTimeMinutes = estimatedTimeMinutes;
        quote.orderId = orderId;
        quote.isSelected = isSelected;
        quote.createdAt = createdAt;
        quote.updatedAt = updatedAt;
        return quote;
    }
}
