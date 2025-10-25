package com.genesisnest.gdpt.shared.events;

import com.genesisnest.gdpt.shared.OrderStatus;

public record OrderStatusChanged(Long orderId, OrderStatus oldStatus, OrderStatus newStatus) {
}
