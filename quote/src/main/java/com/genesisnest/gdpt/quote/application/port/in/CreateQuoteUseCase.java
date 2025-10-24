package com.genesisnest.gdpt.quote.application.port.in;

import com.genesisnest.gdpt.common.Money;
import com.genesisnest.gdpt.quote.domain.AgencyQuote;

public interface CreateQuoteUseCase {

    AgencyQuote createQuote(CreateQuoteCommand command);

    record CreateQuoteCommand(
            String agencyName,
            Money estimatedFee,
            Integer estimatedTimeMinutes,
            Long orderId) {
    }
}