package com.karold.exchange.converter.service.impl;

import com.karold.exchange.model.CurrencyRate;
import com.karold.exchange.model.Money;
import com.karold.exchange.converter.exception.InvalidCurrencyCodeException;
import com.karold.exchange.converter.service.CurrencyRateService;
import com.karold.exchange.converter.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final CurrencyRateService currencyRateService;

    public Money exchangeCurrency(Money value, String targetCurrency, LocalDate effectiveDate) {
        if (isInvalidCurrencyCode(value.getCurrencyCode()) || isInvalidCurrencyCode(targetCurrency)) {
            throw new InvalidCurrencyCodeException();
        }

        CurrencyRate baseCurrencyRate = currencyRateService.getCurrencyRateByDate(value.getCurrencyCode(), effectiveDate);
        CurrencyRate targetCurrencyRate = currencyRateService.getCurrencyRateByDate(targetCurrency, effectiveDate);
        Money plnValue = baseValueToPln(value, baseCurrencyRate);
        return plnToTargetValue(plnValue, targetCurrencyRate);
    }

    private Money baseValueToPln(Money value, CurrencyRate currencyRate) {
        return value.convertToPLN(currencyRate.getRateMid());

    }

    private Money plnToTargetValue(Money value, CurrencyRate currencyRate) {
        return value.convertFromPLN(currencyRate.getCode(), currencyRate.getRateMid());
    }

    private boolean isInvalidCurrencyCode(String currencyCode) {
        return currencyCode.length() != 3;
    }
}
