package com.genesisnest.gdpt.quote.adapter.out.persistence;

import com.genesisnest.gdpt.quote.domain.AgencyQuote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<AgencyQuote, Long> {

    List<AgencyQuote> findByOrderId(Long orderId);

    List<AgencyQuote> findByAgencyName(String agencyName);
}