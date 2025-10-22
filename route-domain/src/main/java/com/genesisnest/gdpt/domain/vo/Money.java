package com.genesisnest.gdpt.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Money {
    private BigDecimal amount;
    private String currency;
    
    public Money add(Money other) {
        if (!Objects.equals(this.currency, other.currency)) {
            throw new IllegalArgumentException("서로 다른 통화는 더할 수 없습니다.");
        }
        
        return new Money(this.amount.add(other.amount), this.currency);
    }
    
    public Money subtract(Money other) {
        if (!Objects.equals(this.currency, other.currency)) {
            throw new IllegalArgumentException("서로 다른 통화는 뺄 수 없습니다.");
        }
        
        return new Money(this.amount.subtract(other.amount), this.currency);
    }
    
    public boolean isGreaterThan(Money other) {
        if (!Objects.equals(this.currency, other.currency)) {
            throw new IllegalArgumentException("서로 다른 통화는 비교할 수 없습니다.");
        }
        
        return this.amount.compareTo(other.amount) > 0;
    }
    
    public boolean isZero() {
        return this.amount.compareTo(BigDecimal.ZERO) == 0;
    }
    
    public static Money of(BigDecimal amount, String currency) {
        return Money.builder()
                .amount(amount)
                .currency(currency)
                .build();
    }
    
    public static Money of(long amount, String currency) {
        return of(BigDecimal.valueOf(amount), currency);
    }
    
    public static Money of(double amount, String currency) {
        return of(BigDecimal.valueOf(amount), currency);
    }
    
    public static Money zero(String currency) {
        return of(BigDecimal.ZERO, currency);
    }
    
    public static Money krw(BigDecimal amount) {
        return of(amount, "KRW");
    }
    
    public static Money krw(long amount) {
        return of(amount, "KRW");
    }
    
    public static Money krw(double amount) {
        return of(amount, "KRW");
    }
}
