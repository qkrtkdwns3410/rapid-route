package com.genesisnest.gdpt.quote.domain;

import com.genesisnest.gdpt.common.BaseEntity;
import com.genesisnest.gdpt.common.Money;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "agency_quotes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AgencyQuote extends BaseEntity {

    @Column(name = "agency_name", nullable = false)
    private String agencyName;

    @Embedded
    private Money estimatedFee;

    @Column(name = "estimated_time_minutes", nullable = false)
    private Integer estimatedTimeMinutes;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "is_selected", nullable = false)
    private Boolean isSelected;

    private AgencyQuote(String agencyName, Money estimatedFee, Integer estimatedTimeMinutes, Long orderId) {
        this.agencyName = agencyName;
        this.estimatedFee = estimatedFee;
        this.estimatedTimeMinutes = estimatedTimeMinutes;
        this.orderId = orderId;
        this.isSelected = false;
    }

    public static AgencyQuote create(String agencyName, Money estimatedFee, Integer estimatedTimeMinutes,
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

        return new AgencyQuote(agencyName, estimatedFee, estimatedTimeMinutes, orderId);
    }

    public void select() {
        this.isSelected = true;
    }

    public void unselect() {
        this.isSelected = false;
    }

    public boolean isCheaperThan(AgencyQuote other) {
        return this.estimatedFee.isLessThan(other.estimatedFee);
    }

    public boolean isFasterThan(AgencyQuote other) {
        return this.estimatedTimeMinutes < other.estimatedTimeMinutes;
    }
}