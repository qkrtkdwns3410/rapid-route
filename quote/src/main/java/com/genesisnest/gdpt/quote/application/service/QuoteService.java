package com.genesisnest.gdpt.quote.application.service;

import com.genesisnest.gdpt.quote.application.port.in.CompareQuotesUseCase;
import com.genesisnest.gdpt.quote.application.port.in.CreateQuoteUseCase;
import com.genesisnest.gdpt.quote.application.port.out.LoadQuotePort;
import com.genesisnest.gdpt.quote.application.port.out.SaveQuotePort;
import com.genesisnest.gdpt.quote.domain.AgencyQuote;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class QuoteService implements CreateQuoteUseCase, CompareQuotesUseCase {

    private final SaveQuotePort saveQuotePort;
    private final LoadQuotePort loadQuotePort;

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
    public AgencyQuote findBestQuote(List<AgencyQuote> quotes, ComparisonStrategy strategy) {
        if (quotes == null || quotes.isEmpty()) {
            throw new IllegalArgumentException("견적 목록이 비어있습니다.");
        }

        return switch (strategy) {
            case LOWEST_FEE -> quotes.stream()
                    .min((q1, q2) -> q1.getEstimatedFee().compareTo(q2.getEstimatedFee()))
                    .orElseThrow(() -> new IllegalStateException("최저가 견적을 찾을 수 없습니다."));
            case FASTEST_TIME -> quotes.stream()
                    .min((q1, q2) -> Integer.compare(q1.getEstimatedTimeMinutes(), q2.getEstimatedTimeMinutes()))
                    .orElseThrow(() -> new IllegalStateException("최단시간 견적을 찾을 수 없습니다."));
        };
    }
}