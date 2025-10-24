package com.genesisnest.gdpt.order.application.port.out;

import com.genesisnest.gdpt.order.domain.Order;

public interface SaveOrderPort {

    Order save(Order order);
}