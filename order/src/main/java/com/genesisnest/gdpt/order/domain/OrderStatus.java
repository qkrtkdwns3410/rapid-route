package com.genesisnest.gdpt.order.domain;

public enum OrderStatus {
    PENDING("대기중"),
    CONFIRMED("확정됨"),
    IN_PROGRESS("진행중"),
    COMPLETED("완료됨"),
    CANCELLED("취소됨");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}