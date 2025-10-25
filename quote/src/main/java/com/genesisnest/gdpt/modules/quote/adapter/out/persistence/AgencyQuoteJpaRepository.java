package com.genesisnest.gdpt.modules.quote.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgencyQuoteJpaRepository extends JpaRepository<AgencyQuoteJpaEntity, Long> {
    List<AgencyQuoteJpaEntity> findByOrderId(Long orderId);

    Optional<AgencyQuoteJpaEntity> findByOrderIdAndIsSelected(Long orderId, Boolean isSelected);
}
