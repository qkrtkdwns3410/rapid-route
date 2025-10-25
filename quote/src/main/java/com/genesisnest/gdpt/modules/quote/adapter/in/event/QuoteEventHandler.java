package com.genesisnest.gdpt.modules.quote.adapter.in.event;

import com.genesisnest.gdpt.modules.quote.application.port.in.CreateQuoteUseCase;
import com.genesisnest.gdpt.shared.events.OrderCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuoteEventHandler {

    private final CreateQuoteUseCase createQuoteUseCase;

    @EventListener
    @Transactional
    public void handle(OrderCreated event) {
        log.info("주문 생성 이벤트 수신: orderId={}, customerName={}",
                event.orderId(), event.customerName());

        // 주문이 생성되면 견적 요청을 자동으로 생성하는 로직을 여기에 추가할 수 있습니다.
        // 예: 여러 대행사에 견적 요청을 보내는 로직
        // createQuoteUseCase.createQuote(new CreateQuoteCommand(...));
    }
}
