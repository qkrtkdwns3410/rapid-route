package com.genesisnest.gdpt.shared.events;

import org.springframework.modulith.events.Externalized;

@Externalized("order-created::#{orderId}")
public record OrderCreated(Long orderId, String customerName, String customerPhone) {
}
