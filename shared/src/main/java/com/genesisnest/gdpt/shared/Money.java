package com.genesisnest.gdpt.shared;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Money {

    private BigDecimal amount;
    private String currency;

    public Money(BigDecimal amount, String currency) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("금액은 0 이상이어야 합니다.");
        }
        if (currency == null || currency.trim().isEmpty()) {
            throw new IllegalArgumentException("통화는 필수입니다.");
        }
        this.amount = amount;
        this.currency = currency;
    }

    public Money(BigDecimal amount) {
        this(amount, "KRW");
    }

    public boolean isLessThan(Money other) {
        return this.amount.compareTo(other.amount) < 0;
    }

    public boolean isGreaterThan(Money other) {
        return this.amount.compareTo(other.amount) > 0;
    }

    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("다른 통화는 더할 수 없습니다.");
        }
        return new Money(this.amount.add(other.amount), this.currency);
    }

    public Money subtract(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("다른 통화는 뺄 수 없습니다.");
        }
        return new Money(this.amount.subtract(other.amount), this.currency);
    }

    public int compareTo(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("다른 통화는 비교할 수 없습니다.");
        }
        return this.amount.compareTo(other.amount);
    }
}
