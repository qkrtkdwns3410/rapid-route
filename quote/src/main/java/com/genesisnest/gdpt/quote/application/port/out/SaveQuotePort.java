package com.genesisnest.gdpt.quote.application.port.out;

import com.genesisnest.gdpt.quote.domain.AgencyQuote;

import java.util.List;

public interface SaveQuotePort {

    AgencyQuote save(AgencyQuote quote);

    List<AgencyQuote> saveAll(List<AgencyQuote> quotes);
}