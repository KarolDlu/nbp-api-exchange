package com.karold.exchange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Money {

    private String currencyCode;

    private BigDecimal value;

    public Money convertToPLN(BigDecimal midRate) {
        return new Money("PLN", this.value.multiply(midRate));
    }

    public Money convertFromPLN(String currencyCode, BigDecimal midRate) {
        return new Money(currencyCode, this.value.divide(midRate, RoundingMode.HALF_UP)
                .setScale(2, RoundingMode.HALF_UP));
    }
}
