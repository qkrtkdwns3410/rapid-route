package com.genesisnest.gdpt.quote.application.port.in;

import com.genesisnest.gdpt.quote.domain.AgencyQuote;

import java.util.List;

public interface CompareQuotesUseCase {

    AgencyQuote findBestQuote(List<AgencyQuote> quotes, ComparisonStrategy strategy);

    enum ComparisonStrategy {
        LOWEST_FEE,
        FASTEST_TIME
    }
}