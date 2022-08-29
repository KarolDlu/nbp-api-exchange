package com.karold.exchange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"code", "effectiveDate"}))
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CurrencyRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Embedded
    private Rate rate;

    public CurrencyRate(String code, Rate rate) {
        this.code = code;
        this.rate = rate;
    }

    public BigDecimal getRateMid() {
        return this.rate.getMid();
    }
}
