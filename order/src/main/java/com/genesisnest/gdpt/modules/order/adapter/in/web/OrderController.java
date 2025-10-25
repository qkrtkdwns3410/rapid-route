package com.genesisnest.gdpt.modules.order.adapter.in.web;

import com.genesisnest.gdpt.modules.order.adapter.in.web.dto.CreateOrderRequest;
import com.genesisnest.gdpt.modules.order.adapter.in.web.dto.UpdateOrderStatusRequest;
import com.genesisnest.gdpt.modules.order.application.port.in.CreateOrderUseCase;
import com.genesisnest.gdpt.modules.order.application.port.in.QueryOrderUseCase;
import com.genesisnest.gdpt.modules.order.application.port.in.UpdateOrderStatusUseCase;
import com.genesisnest.gdpt.modules.order.domain.Order;
import com.genesisnest.gdpt.shared.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;
    private final QueryOrderUseCase queryOrderUseCase;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
        CreateOrderUseCase.CreateOrderCommand command = new CreateOrderUseCase.CreateOrderCommand(
                request.customerName(),
                request.customerPhone(),
                request.street(),
                request.city(),
                request.zipCode(),
                request.amount(),
                request.description());

        Order order = createOrderUseCase.createOrder(command);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId,
            @RequestBody UpdateOrderStatusRequest request) {
        UpdateOrderStatusUseCase.UpdateOrderStatusCommand command = new UpdateOrderStatusUseCase.UpdateOrderStatusCommand(
                orderId, request.status());

        Order order = updateOrderStatusUseCase.updateOrderStatus(command);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
        return queryOrderUseCase.findOrderById(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = queryOrderUseCase.findAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable OrderStatus status) {
        List<Order> orders = queryOrderUseCase.findOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }
}
