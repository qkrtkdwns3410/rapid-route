package com.genesisnest.gdpt.modules.quote.adapter.out.persistence;

import com.genesisnest.gdpt.shared.BaseEntity;
import com.genesisnest.gdpt.shared.Money;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "agency_quotes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AgencyQuoteJpaEntity extends BaseEntity {

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

    public AgencyQuoteJpaEntity(String agencyName, Money estimatedFee, Integer estimatedTimeMinutes,
            Long orderId, Boolean isSelected) {
        this.agencyName = agencyName;
        this.estimatedFee = estimatedFee;
        this.estimatedTimeMinutes = estimatedTimeMinutes;
        this.orderId = orderId;
        this.isSelected = isSelected;
    }

    public void updateSelection(Boolean isSelected) {
        this.isSelected = isSelected;
    }

    // Factory method for reconstruction from domain entity
    public static AgencyQuoteJpaEntity reconstruct(Long id, String agencyName, Money estimatedFee,
            Integer estimatedTimeMinutes, Long orderId, Boolean isSelected,
            java.time.LocalDateTime createdAt, java.time.LocalDateTime updatedAt) {
        AgencyQuoteJpaEntity entity = new AgencyQuoteJpaEntity();
        entity.setId(id);
        entity.agencyName = agencyName;
        entity.estimatedFee = estimatedFee;
        entity.estimatedTimeMinutes = estimatedTimeMinutes;
        entity.orderId = orderId;
        entity.isSelected = isSelected;
        entity.setCreatedAt(createdAt);
        entity.setUpdatedAt(updatedAt);
        return entity;
    }
}
