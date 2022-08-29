package com.karold.exchange.converter.service;

import com.karold.exchange.model.Money;

import java.time.LocalDate;

public interface ExchangeService {

    Money exchangeCurrency(Money value, String targetCurrency, LocalDate effectiveDate);
}
