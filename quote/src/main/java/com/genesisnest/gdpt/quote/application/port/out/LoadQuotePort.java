package com.genesisnest.gdpt.quote.application.port.out;

import com.genesisnest.gdpt.quote.domain.AgencyQuote;

import java.util.List;
import java.util.Optional;

public interface LoadQuotePort {

    Optional<AgencyQuote> findById(Long id);

    List<AgencyQuote> findAll();

    List<AgencyQuote> findByOrderId(Long orderId);

    List<AgencyQuote> findByAgencyName(String agencyName);
}