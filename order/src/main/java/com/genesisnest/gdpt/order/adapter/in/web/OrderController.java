package com.genesisnest.gdpt.order.adapter.in.web;

import com.genesisnest.gdpt.common.Address;
import com.genesisnest.gdpt.common.Money;
import com.genesisnest.gdpt.order.application.port.in.CreateOrderUseCase;
import com.genesisnest.gdpt.order.application.port.in.UpdateOrderStatusUseCase;
import com.genesisnest.gdpt.order.domain.Order;
import com.genesisnest.gdpt.order.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        Address address = Address.of(
                request.street(),
                request.city(),
                request.state(),
                request.zipCode(),
                request.country());

        Money totalAmount = Money.of(new BigDecimal(request.totalAmount()));

        CreateOrderUseCase.CreateOrderCommand command = new CreateOrderUseCase.CreateOrderCommand(
                request.customerName(),
                request.customerPhone(),
                address,
                totalAmount,
                request.description());

        Order order = createOrderUseCase.createOrder(command);

        return ResponseEntity.ok(OrderResponse.from(order));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody UpdateOrderStatusRequest request) {

        Order order = updateOrderStatusUseCase.updateOrderStatus(orderId, request.status());

        return ResponseEntity.ok(OrderResponse.from(order));
    }

    public record CreateOrderRequest(
            String customerName,
            String customerPhone,
            String street,
            String city,
            String state,
            String zipCode,
            String country,
            String totalAmount,
            String description) {
    }

    public record UpdateOrderStatusRequest(OrderStatus status) {
    }

    public record OrderResponse(
            Long id,
            String customerName,
            String customerPhone,
            String deliveryAddress,
            String totalAmount,
            OrderStatus status,
            String description) {
        public static OrderResponse from(Order order) {
            return new OrderResponse(
                    order.getId(),
                    order.getCustomerName(),
                    order.getCustomerPhone(),
                    order.getDeliveryAddress().getFullAddress(),
                    order.getTotalAmount().toString(),
                    order.getStatus(),
                    order.getDescription());
        }
    }
}