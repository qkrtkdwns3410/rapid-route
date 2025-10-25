package com.genesisnest.gdpt.modules.order;

import com.genesisnest.gdpt.shared.Address;
import com.genesisnest.gdpt.shared.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class OrderIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Test
    void shouldCreateOrder() {
        // Given
        String customerName = "홍길동";
        String customerPhone = "010-1234-5678";
        String street = "강남대로 123";
        String city = "서울";
        String zipCode = "12345";
        BigDecimal amount = new BigDecimal("15000");
        String description = "테스트 주문";

        // When
        Order order = orderService.createOrder(customerName, customerPhone, street, city, zipCode, amount, description);

        // Then
        assertThat(order).isNotNull();
        assertThat(order.getCustomerName()).isEqualTo(customerName);
        assertThat(order.getCustomerPhone()).isEqualTo(customerPhone);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(order.getTotalAmount().getAmount()).isEqualTo(amount);
    }

    @Test
    void shouldUpdateOrderStatus() {
        // Given
        Order order = orderService.createOrder("홍길동", "010-1234-5678", 
                "강남대로 123", "서울", "12345", new BigDecimal("15000"), "테스트 주문");

        // When
        Order updatedOrder = orderService.updateOrderStatus(order.getId(), OrderStatus.CONFIRMED);

        // Then
        assertThat(updatedOrder.getStatus()).isEqualTo(OrderStatus.CONFIRMED);
    }

    @Test
    void shouldFindOrderById() {
        // Given
        Order createdOrder = orderService.createOrder("홍길동", "010-1234-5678", 
                "강남대로 123", "서울", "12345", new BigDecimal("15000"), "테스트 주문");

        // When
        Optional<Order> foundOrder = orderService.findOrderById(createdOrder.getId());

        // Then
        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get().getId()).isEqualTo(createdOrder.getId());
    }
}
