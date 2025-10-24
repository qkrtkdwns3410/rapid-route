package com.genesisnest.gdpt.order.adapter.out.persistence;

import com.genesisnest.gdpt.order.domain.Order;
import com.genesisnest.gdpt.order.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(OrderStatus status);
}