package com.genesisnest.gdpt.modules.quote.adapter.in.web.dto;

import java.math.BigDecimal;

public record CreateQuoteRequest(
        String agencyName,
        BigDecimal estimatedFee,
        Integer estimatedTimeMinutes,
        Long orderId) {
}
