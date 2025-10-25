package com.genesisnest.gdpt.modules.order.application.service;

import com.genesisnest.gdpt.modules.order.application.port.in.CreateOrderUseCase;
import com.genesisnest.gdpt.modules.order.application.port.in.QueryOrderUseCase;
import com.genesisnest.gdpt.modules.order.application.port.in.UpdateOrderStatusUseCase;
import com.genesisnest.gdpt.modules.order.application.port.out.LoadOrderPort;
import com.genesisnest.gdpt.modules.order.application.port.out.SaveOrderPort;
import com.genesisnest.gdpt.modules.order.domain.Order;
import com.genesisnest.gdpt.shared.Address;
import com.genesisnest.gdpt.shared.Money;
import com.genesisnest.gdpt.shared.OrderStatus;
import com.genesisnest.gdpt.shared.events.OrderCreated;
import com.genesisnest.gdpt.shared.events.OrderStatusChanged;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService implements CreateOrderUseCase, UpdateOrderStatusUseCase, QueryOrderUseCase {

    private final LoadOrderPort loadOrderPort;
    private final SaveOrderPort saveOrderPort;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Order createOrder(CreateOrderUseCase.CreateOrderCommand command) {
        Address deliveryAddress = new Address(command.street(), command.city(), command.zipCode());
        Money totalAmount = new Money(command.amount());

        Order order = Order.create(
                command.customerName(),
                command.customerPhone(),
                deliveryAddress,
                totalAmount,
                command.description());

        Order savedOrder = saveOrderPort.save(order);

        // 주문 생성 이벤트 발행
        eventPublisher.publishEvent(
                new OrderCreated(savedOrder.getId(), savedOrder.getCustomerName(), savedOrder.getCustomerPhone()));

        return savedOrder;
    }

    @Override
    public Order updateOrderStatus(UpdateOrderStatusUseCase.UpdateOrderStatusCommand command) {
        Order order = loadOrderPort.findById(command.orderId())
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다: " + command.orderId()));

        OrderStatus oldStatus = order.getStatus();
        order.updateStatus(command.status());
        Order savedOrder = saveOrderPort.save(order);

        // 주문 상태 변경 이벤트 발행
        eventPublisher.publishEvent(new OrderStatusChanged(savedOrder.getId(), oldStatus, savedOrder.getStatus()));

        return savedOrder;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findOrderById(Long orderId) {
        return loadOrderPort.findById(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAllOrders() {
        return loadOrderPort.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findOrdersByStatus(OrderStatus status) {
        return loadOrderPort.findByStatus(status);
    }
}
