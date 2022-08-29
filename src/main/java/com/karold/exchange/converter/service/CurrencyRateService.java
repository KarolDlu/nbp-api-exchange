package com.karold.exchange.converter.service;

import com.karold.exchange.model.CurrencyRate;

import java.time.LocalDate;

public interface CurrencyRateService {

    CurrencyRate getCurrencyRateByDate(String currencyCode, LocalDate effectiveDate);
}
