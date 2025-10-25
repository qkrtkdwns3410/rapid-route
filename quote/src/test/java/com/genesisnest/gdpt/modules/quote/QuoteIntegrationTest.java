package com.genesisnest.gdpt.modules.quote;

import com.genesisnest.gdpt.modules.order.Order;
import com.genesisnest.gdpt.modules.order.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class QuoteIntegrationTest {

    @Autowired
    private QuoteService quoteService;

    @Autowired
    private OrderService orderService;

    @Test
    void shouldCreateQuote() {
        // Given
        Order order = orderService.createOrder("홍길동", "010-1234-5678", 
                "강남대로 123", "서울", "12345", new BigDecimal("15000"), "테스트 주문");
        
        String agencyName = "배달의민족";
        BigDecimal estimatedFee = new BigDecimal("3000");
        Integer estimatedTimeMinutes = 30;

        // When
        AgencyQuote quote = quoteService.createQuote(agencyName, estimatedFee, estimatedTimeMinutes, order.getId());

        // Then
        assertThat(quote).isNotNull();
        assertThat(quote.getAgencyName()).isEqualTo(agencyName);
        assertThat(quote.getEstimatedFee().getAmount()).isEqualTo(estimatedFee);
        assertThat(quote.getEstimatedTimeMinutes()).isEqualTo(estimatedTimeMinutes);
        assertThat(quote.getOrderId()).isEqualTo(order.getId());
        assertThat(quote.getIsSelected()).isFalse();
    }

    @Test
    void shouldSelectQuote() {
        // Given
        Order order = orderService.createOrder("홍길동", "010-1234-5678", 
                "강남대로 123", "서울", "12345", new BigDecimal("15000"), "테스트 주문");
        
        AgencyQuote quote = quoteService.createQuote("배달의민족", new BigDecimal("3000"), 30, order.getId());

        // When
        AgencyQuote selectedQuote = quoteService.selectQuote(quote.getId());

        // Then
        assertThat(selectedQuote.getIsSelected()).isTrue();
    }

    @Test
    void shouldFindCheapestQuote() {
        // Given
        Order order = orderService.createOrder("홍길동", "010-1234-5678", 
                "강남대로 123", "서울", "12345", new BigDecimal("15000"), "테스트 주문");
        
        quoteService.createQuote("배달의민족", new BigDecimal("3000"), 30, order.getId());
        quoteService.createQuote("요기요", new BigDecimal("2500"), 35, order.getId());
        quoteService.createQuote("쿠팡이츠", new BigDecimal("4000"), 25, order.getId());

        // When
        AgencyQuote cheapestQuote = quoteService.findCheapestQuote(order.getId());

        // Then
        assertThat(cheapestQuote).isNotNull();
        assertThat(cheapestQuote.getAgencyName()).isEqualTo("요기요");
        assertThat(cheapestQuote.getEstimatedFee().getAmount()).isEqualTo(new BigDecimal("2500"));
    }

    @Test
    void shouldFindFastestQuote() {
        // Given
        Order order = orderService.createOrder("홍길동", "010-1234-5678", 
                "강남대로 123", "서울", "12345", new BigDecimal("15000"), "테스트 주문");
        
        quoteService.createQuote("배달의민족", new BigDecimal("3000"), 30, order.getId());
        quoteService.createQuote("요기요", new BigDecimal("2500"), 35, order.getId());
        quoteService.createQuote("쿠팡이츠", new BigDecimal("4000"), 25, order.getId());

        // When
        AgencyQuote fastestQuote = quoteService.findFastestQuote(order.getId());

        // Then
        assertThat(fastestQuote).isNotNull();
        assertThat(fastestQuote.getAgencyName()).isEqualTo("쿠팡이츠");
        assertThat(fastestQuote.getEstimatedTimeMinutes()).isEqualTo(25);
    }
}
