package com.genesisnest.gdpt.modules.quote.adapter.out.persistence;

import com.genesisnest.gdpt.modules.quote.application.port.out.LoadQuotePort;
import com.genesisnest.gdpt.modules.quote.application.port.out.SaveQuotePort;
import com.genesisnest.gdpt.modules.quote.domain.AgencyQuote;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AgencyQuotePersistenceAdapter implements LoadQuotePort, SaveQuotePort {

    private final AgencyQuoteJpaRepository agencyQuoteJpaRepository;
    private final AgencyQuoteMapper agencyQuoteMapper;

    @Override
    public List<AgencyQuote> findByOrderId(Long orderId) {
        return agencyQuoteJpaRepository.findByOrderId(orderId).stream()
                .map(agencyQuoteMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<AgencyQuote> findByOrderIdAndIsSelected(Long orderId, Boolean isSelected) {
        return agencyQuoteJpaRepository.findByOrderIdAndIsSelected(orderId, isSelected)
                .map(agencyQuoteMapper::toDomain);
    }

    @Override
    public List<AgencyQuote> findAll() {
        return agencyQuoteJpaRepository.findAll().stream()
                .map(agencyQuoteMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<AgencyQuote> findById(Long quoteId) {
        return agencyQuoteJpaRepository.findById(quoteId)
                .map(agencyQuoteMapper::toDomain);
    }

    @Override
    public AgencyQuote save(AgencyQuote quote) {
        AgencyQuoteJpaEntity entity = agencyQuoteMapper.toJpaEntity(quote);
        AgencyQuoteJpaEntity savedEntity = agencyQuoteJpaRepository.save(entity);
        return agencyQuoteMapper.toDomain(savedEntity);
    }
}
