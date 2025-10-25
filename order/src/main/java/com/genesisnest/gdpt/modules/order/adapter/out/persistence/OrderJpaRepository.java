package com.genesisnest.gdpt.modules.order.adapter.out.persistence;

import com.genesisnest.gdpt.shared.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, Long> {
    List<OrderJpaEntity> findByStatus(OrderStatus status);
}
