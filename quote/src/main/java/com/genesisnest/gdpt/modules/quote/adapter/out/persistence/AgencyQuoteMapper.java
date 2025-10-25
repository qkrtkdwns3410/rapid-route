package com.genesisnest.gdpt.modules.quote.adapter.out.persistence;

import com.genesisnest.gdpt.modules.quote.domain.AgencyQuote;
import org.springframework.stereotype.Component;

@Component
public class AgencyQuoteMapper {

    public AgencyQuoteJpaEntity toJpaEntity(AgencyQuote quote) {
        return AgencyQuoteJpaEntity.reconstruct(
                quote.getId(),
                quote.getAgencyName(),
                quote.getEstimatedFee(),
                quote.getEstimatedTimeMinutes(),
                quote.getOrderId(),
                quote.getIsSelected(),
                quote.getCreatedAt(),
                quote.getUpdatedAt());
    }

    public AgencyQuote toDomain(AgencyQuoteJpaEntity entity) {
        return AgencyQuote.reconstruct(
                entity.getId(),
                entity.getAgencyName(),
                entity.getEstimatedFee(),
                entity.getEstimatedTimeMinutes(),
                entity.getOrderId(),
                entity.getIsSelected(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}
