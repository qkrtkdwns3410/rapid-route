package com.genesisnest.gdpt.modules.quote.application.service;

import com.genesisnest.gdpt.modules.quote.application.port.in.CreateQuoteUseCase;
import com.genesisnest.gdpt.modules.quote.application.port.in.QueryQuoteUseCase;
import com.genesisnest.gdpt.modules.quote.application.port.in.SelectQuoteUseCase;
import com.genesisnest.gdpt.modules.quote.application.port.out.LoadQuotePort;
import com.genesisnest.gdpt.modules.quote.application.port.out.SaveQuotePort;
import com.genesisnest.gdpt.modules.quote.domain.AgencyQuote;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuoteService implements CreateQuoteUseCase, SelectQuoteUseCase, QueryQuoteUseCase {

    private final LoadQuotePort loadQuotePort;
    private final SaveQuotePort saveQuotePort;

    @Override
    public AgencyQuote createQuote(CreateQuoteUseCase.CreateQuoteCommand command) {
        AgencyQuote quote = AgencyQuote.create(
                command.agencyName(),
                command.estimatedFee(),
                command.estimatedTimeMinutes(),
                command.orderId());
        return saveQuotePort.save(quote);
    }

    @Override
    public AgencyQuote selectQuote(SelectQuoteUseCase.SelectQuoteCommand command) {
        AgencyQuote quote = loadQuotePort.findById(command.quoteId())
                .orElseThrow(() -> new IllegalArgumentException("견적을 찾을 수 없습니다: " + command.quoteId()));

        quote.select();
        return saveQuotePort.save(quote);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgencyQuote> findQuotesByOrderId(Long orderId) {
        return loadQuotePort.findByOrderId(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AgencyQuote> findSelectedQuoteByOrderId(Long orderId) {
        return loadQuotePort.findByOrderIdAndIsSelected(orderId, true);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgencyQuote> findAllQuotes() {
        return loadQuotePort.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public AgencyQuote findCheapestQuote(Long orderId) {
        List<AgencyQuote> quotes = loadQuotePort.findByOrderId(orderId);
        return quotes.stream()
                .min((q1, q2) -> q1.getEstimatedFee().compareTo(q2.getEstimatedFee()))
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public AgencyQuote findFastestQuote(Long orderId) {
        List<AgencyQuote> quotes = loadQuotePort.findByOrderId(orderId);
        return quotes.stream()
                .min((q1, q2) -> q1.getEstimatedTimeMinutes().compareTo(q2.getEstimatedTimeMinutes()))
                .orElse(null);
    }
}
