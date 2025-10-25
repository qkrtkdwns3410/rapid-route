package com.genesisnest.gdpt.modules.quote.application.port.out;

import com.genesisnest.gdpt.modules.quote.domain.AgencyQuote;

public interface SaveQuotePort {

    AgencyQuote save(AgencyQuote quote);
}
