package com.genesisnest.gdpt.modules.quote.application.port.out;

import com.genesisnest.gdpt.modules.quote.domain.AgencyQuote;

import java.util.List;
import java.util.Optional;

public interface LoadQuotePort {

    List<AgencyQuote> findByOrderId(Long orderId);

    Optional<AgencyQuote> findByOrderIdAndIsSelected(Long orderId, Boolean isSelected);

    List<AgencyQuote> findAll();

    Optional<AgencyQuote> findById(Long quoteId);
}
