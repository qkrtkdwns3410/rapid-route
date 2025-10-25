package com.genesisnest.gdpt.modules.quote.application.port.in;

import com.genesisnest.gdpt.modules.quote.domain.AgencyQuote;

import java.util.List;
import java.util.Optional;

public interface QueryQuoteUseCase {

    List<AgencyQuote> findQuotesByOrderId(Long orderId);

    Optional<AgencyQuote> findSelectedQuoteByOrderId(Long orderId);

    List<AgencyQuote> findAllQuotes();

    AgencyQuote findCheapestQuote(Long orderId);

    AgencyQuote findFastestQuote(Long orderId);
}
