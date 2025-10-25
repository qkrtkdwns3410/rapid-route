package com.genesisnest.gdpt.modules.order.application.port.out;

import com.genesisnest.gdpt.modules.order.domain.Order;

public interface SaveOrderPort {

    Order save(Order order);
}
