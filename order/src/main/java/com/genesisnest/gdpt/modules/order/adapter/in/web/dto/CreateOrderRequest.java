package com.genesisnest.gdpt.modules.order.adapter.in.web.dto;

import java.math.BigDecimal;

public record CreateOrderRequest(
        String customerName,
        String customerPhone,
        String street,
        String city,
        String zipCode,
        BigDecimal amount,
        String description) {
}
