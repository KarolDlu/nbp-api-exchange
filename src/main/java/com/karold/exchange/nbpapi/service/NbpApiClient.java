package com.karold.exchange.nbpapi.service;

import com.karold.exchange.model.CurrencyRate;

import java.time.LocalDate;

public interface NbpApiClient {

    CurrencyRate getCurrencyRate(String currencyCode, LocalDate date);
}
