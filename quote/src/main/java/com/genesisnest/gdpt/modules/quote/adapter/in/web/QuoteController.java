package com.genesisnest.gdpt.modules.quote.adapter.in.web;

import com.genesisnest.gdpt.modules.quote.adapter.in.web.dto.CreateQuoteRequest;
import com.genesisnest.gdpt.modules.quote.application.port.in.CreateQuoteUseCase;
import com.genesisnest.gdpt.modules.quote.application.port.in.QueryQuoteUseCase;
import com.genesisnest.gdpt.modules.quote.application.port.in.SelectQuoteUseCase;
import com.genesisnest.gdpt.modules.quote.domain.AgencyQuote;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quotes")
@RequiredArgsConstructor
public class QuoteController {

    private final CreateQuoteUseCase createQuoteUseCase;
    private final SelectQuoteUseCase selectQuoteUseCase;
    private final QueryQuoteUseCase queryQuoteUseCase;

    @PostMapping
    public ResponseEntity<AgencyQuote> createQuote(@RequestBody CreateQuoteRequest request) {
        CreateQuoteUseCase.CreateQuoteCommand command = new CreateQuoteUseCase.CreateQuoteCommand(
                request.agencyName(),
                request.estimatedFee(),
                request.estimatedTimeMinutes(),
                request.orderId());

        AgencyQuote quote = createQuoteUseCase.createQuote(command);
        return ResponseEntity.ok(quote);
    }

    @PutMapping("/{quoteId}/select")
    public ResponseEntity<AgencyQuote> selectQuote(@PathVariable Long quoteId) {
        SelectQuoteUseCase.SelectQuoteCommand command = new SelectQuoteUseCase.SelectQuoteCommand(quoteId);

        AgencyQuote quote = selectQuoteUseCase.selectQuote(command);
        return ResponseEntity.ok(quote);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<AgencyQuote>> getQuotesByOrderId(@PathVariable Long orderId) {
        List<AgencyQuote> quotes = queryQuoteUseCase.findQuotesByOrderId(orderId);
        return ResponseEntity.ok(quotes);
    }

    @GetMapping("/order/{orderId}/selected")
    public ResponseEntity<AgencyQuote> getSelectedQuoteByOrderId(@PathVariable Long orderId) {
        return queryQuoteUseCase.findSelectedQuoteByOrderId(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AgencyQuote>> getAllQuotes() {
        List<AgencyQuote> quotes = queryQuoteUseCase.findAllQuotes();
        return ResponseEntity.ok(quotes);
    }

    @GetMapping("/order/{orderId}/cheapest")
    public ResponseEntity<AgencyQuote> getCheapestQuote(@PathVariable Long orderId) {
        AgencyQuote quote = queryQuoteUseCase.findCheapestQuote(orderId);
        return quote != null ? ResponseEntity.ok(quote) : ResponseEntity.notFound().build();
    }

    @GetMapping("/order/{orderId}/fastest")
    public ResponseEntity<AgencyQuote> getFastestQuote(@PathVariable Long orderId) {
        AgencyQuote quote = queryQuoteUseCase.findFastestQuote(orderId);
        return quote != null ? ResponseEntity.ok(quote) : ResponseEntity.notFound().build();
    }
}
