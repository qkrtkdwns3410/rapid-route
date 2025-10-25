package com.genesisnest.gdpt.modules.quote.application.port.in;

import com.genesisnest.gdpt.modules.quote.domain.AgencyQuote;

public interface SelectQuoteUseCase {

    AgencyQuote selectQuote(SelectQuoteCommand command);

    record SelectQuoteCommand(Long quoteId) {
    }
}
