package com.genesisnest.gdpt.modules.quote.application.port.in;

import com.genesisnest.gdpt.modules.quote.domain.AgencyQuote;

import java.math.BigDecimal;

public interface CreateQuoteUseCase {

    AgencyQuote createQuote(CreateQuoteCommand command);

    record CreateQuoteCommand(
            String agencyName,
            BigDecimal estimatedFee,
            Integer estimatedTimeMinutes,
            Long orderId) {
    }
}
