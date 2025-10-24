package com.genesisnest.gdpt.order.application.service;

import com.genesisnest.gdpt.order.application.port.in.CreateOrderUseCase;
import com.genesisnest.gdpt.order.application.port.in.UpdateOrderStatusUseCase;
import com.genesisnest.gdpt.order.application.port.out.LoadOrderPort;
import com.genesisnest.gdpt.order.application.port.out.SaveOrderPort;
import com.genesisnest.gdpt.order.domain.Order;
import com.genesisnest.gdpt.order.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService implements CreateOrderUseCase, UpdateOrderStatusUseCase {

    private final SaveOrderPort saveOrderPort;
    private final LoadOrderPort loadOrderPort;

    @Override
    public Order createOrder(CreateOrderUseCase.CreateOrderCommand command) {
        Order order = Order.create(
                command.customerName(),
                command.customerPhone(),
                command.deliveryAddress(),
                command.totalAmount(),
                command.description());

        return saveOrderPort.save(order);
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = loadOrderPort.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다: " + orderId));

        order.updateStatus(newStatus);

        return saveOrderPort.save(order);
    }
}