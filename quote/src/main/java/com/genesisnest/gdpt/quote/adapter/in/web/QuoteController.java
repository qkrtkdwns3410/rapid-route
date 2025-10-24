package com.genesisnest.gdpt.quote.adapter.in.web;

import com.genesisnest.gdpt.common.Money;
import com.genesisnest.gdpt.quote.application.port.in.CompareQuotesUseCase;
import com.genesisnest.gdpt.quote.application.port.in.CreateQuoteUseCase;
import com.genesisnest.gdpt.quote.application.port.out.LoadQuotePort;
import com.genesisnest.gdpt.quote.domain.AgencyQuote;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/quotes")
@RequiredArgsConstructor
public class QuoteController {

    private final CreateQuoteUseCase createQuoteUseCase;
    private final CompareQuotesUseCase compareQuotesUseCase;
    private final LoadQuotePort loadQuotePort;

    @PostMapping
    public ResponseEntity<QuoteResponse> createQuote(@RequestBody CreateQuoteRequest request) {
        Money estimatedFee = Money.of(new BigDecimal(request.estimatedFee()));

        CreateQuoteUseCase.CreateQuoteCommand command = new CreateQuoteUseCase.CreateQuoteCommand(
                request.agencyName(),
                estimatedFee,
                request.estimatedTimeMinutes(),
                request.orderId());

        AgencyQuote quote = createQuoteUseCase.createQuote(command);

        return ResponseEntity.ok(QuoteResponse.from(quote));
    }

    @PostMapping("/compare")
    public ResponseEntity<QuoteResponse> compareQuotes(@RequestBody CompareQuotesRequest request) {
        List<AgencyQuote> quotes = loadQuotePort.findByOrderId(request.orderId());

        AgencyQuote bestQuote = compareQuotesUseCase.findBestQuote(
                quotes,
                request.strategy());

        return ResponseEntity.ok(QuoteResponse.from(bestQuote));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<QuoteResponse>> getQuotesByOrder(@PathVariable Long orderId) {
        List<AgencyQuote> quotes = loadQuotePort.findByOrderId(orderId);
        List<QuoteResponse> responses = quotes.stream()
                .map(QuoteResponse::from)
                .toList();

        return ResponseEntity.ok(responses);
    }

    public record CreateQuoteRequest(
            String agencyName,
            String estimatedFee,
            Integer estimatedTimeMinutes,
            Long orderId) {
    }

    public record CompareQuotesRequest(
            Long orderId,
            CompareQuotesUseCase.ComparisonStrategy strategy) {
    }

    public record QuoteResponse(
            Long id,
            String agencyName,
            String estimatedFee,
            Integer estimatedTimeMinutes,
            Long orderId,
            Boolean isSelected) {
        public static QuoteResponse from(AgencyQuote quote) {
            return new QuoteResponse(
                    quote.getId(),
                    quote.getAgencyName(),
                    quote.getEstimatedFee().toString(),
                    quote.getEstimatedTimeMinutes(),
                    quote.getOrderId(),
                    quote.getIsSelected());
        }
    }
}