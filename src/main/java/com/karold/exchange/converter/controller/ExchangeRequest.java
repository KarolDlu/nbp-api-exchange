package com.karold.exchange.converter.controller;


import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.karold.exchange.model.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ExchangeRequest {

    @JsonUnwrapped
    private Money value;

    private String targetCurrencyCode;

    private LocalDate effectiveDate;
}
