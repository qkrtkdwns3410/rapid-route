package com.genesisnest.gdpt.quote.adapter.out.persistence;

import com.genesisnest.gdpt.quote.application.port.out.LoadQuotePort;
import com.genesisnest.gdpt.quote.application.port.out.SaveQuotePort;
import com.genesisnest.gdpt.quote.domain.AgencyQuote;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QuotePersistenceAdapter implements SaveQuotePort, LoadQuotePort {

    private final QuoteRepository quoteRepository;

    @Override
    public AgencyQuote save(AgencyQuote quote) {
        return quoteRepository.save(quote);
    }

    @Override
    public List<AgencyQuote> saveAll(List<AgencyQuote> quotes) {
        return quoteRepository.saveAll(quotes);
    }

    @Override
    public Optional<AgencyQuote> findById(Long id) {
        return quoteRepository.findById(id);
    }

    @Override
    public List<AgencyQuote> findAll() {
        return quoteRepository.findAll();
    }

    @Override
    public List<AgencyQuote> findByOrderId(Long orderId) {
        return quoteRepository.findByOrderId(orderId);
    }

    @Override
    public List<AgencyQuote> findByAgencyName(String agencyName) {
        return quoteRepository.findByAgencyName(agencyName);
    }
}