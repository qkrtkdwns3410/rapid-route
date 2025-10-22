package com.genesisnest.gdpt.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    
    PENDING("대기중"),
    QUOTED("견적완료"),
    CONFIRMED("확정됨"),
    IN_TRANSIT("배송중"),
    DELIVERED("배송완료"),
    CANCELLED("취소됨");
    
    private final String description;
    
    public boolean canTransitionTo(OrderStatus targetStatus) {
        return switch (this) {
            case PENDING -> targetStatus == QUOTED || targetStatus == CANCELLED;
            case QUOTED -> targetStatus == CONFIRMED || targetStatus == CANCELLED;
            case CONFIRMED -> targetStatus == IN_TRANSIT || targetStatus == CANCELLED;
            case IN_TRANSIT -> targetStatus == DELIVERED || targetStatus == CANCELLED;
            case DELIVERED, CANCELLED -> false;
        };
    }
}
