package com.karold.exchange.converter.service;

import com.karold.exchange.converter.exception.InvalidCurrencyCodeException;
import com.karold.exchange.converter.service.impl.CurrencyRateServiceImpl;
import com.karold.exchange.converter.service.impl.ExchangeServiceImpl;
import com.karold.exchange.model.CurrencyRate;
import com.karold.exchange.model.Money;
import com.karold.exchange.model.Rate;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeServiceTest {

    @Mock
    private CurrencyRateServiceImpl currencyRateService;

    @InjectMocks
    private ExchangeServiceImpl exchangeService;

    @Test
    public void exchangeCurrency_validDataGiven_shouldReturnConvertedCurrency() {
        when(currencyRateService.getCurrencyRateByDate(any(String.class), any(LocalDate.class)))
                .thenAnswer(args -> {
                    Rate rate = new Rate(args.getArgument(1), BigDecimal.valueOf(4.87));
                    String currencyCode = args.getArgument(0);
                    return new CurrencyRate(currencyCode, rate);
                })
                .thenAnswer(args -> {
                    Rate rate = new Rate(args.getArgument(1), BigDecimal.valueOf(4.73));
                    String currencyCode = args.getArgument(0);
                    return new CurrencyRate(currencyCode, rate);
                });
        Money money = new Money("EUR", BigDecimal.valueOf(125));

        Money result = exchangeService.exchangeCurrency(
                money, "USD", LocalDate.of(2022, 8, 17));

        verify(currencyRateService, times(2))
                .getCurrencyRateByDate(any(String.class), any(LocalDate.class));
        Assertions.assertEquals("USD", result.getCurrencyCode());
        Assertions.assertEquals(BigDecimal.valueOf(128.70).setScale(2, RoundingMode.HALF_UP), result.getValue());
    }

    @Test
    public void exchangeCurrency_manyFractionalDigits_shouldReturnRoundedValue() {
        when(currencyRateService.getCurrencyRateByDate(any(String.class), any(LocalDate.class)))
                .thenAnswer(args -> {
                    Rate rate = new Rate(args.getArgument(1), BigDecimal.valueOf(4.8767));
                    String currencyCode = args.getArgument(0);
                    return new CurrencyRate(currencyCode, rate);
                })
                .thenAnswer(args -> {
                    Rate rate = new Rate(args.getArgument(1), BigDecimal.valueOf(4.7312));
                    String currencyCode = args.getArgument(0);
                    return new CurrencyRate(currencyCode, rate);
                });
        Money money = new Money("EUR", BigDecimal.valueOf(125));

        Money result = exchangeService.exchangeCurrency(
                money, "USD", LocalDate.of(2022, 8, 17));

        Assertions.assertEquals("USD", result.getCurrencyCode());
        Assertions.assertEquals(2, result.getValue().scale());
        Assertions.assertEquals(BigDecimal.valueOf(128.84).setScale(2, RoundingMode.HALF_UP), result.getValue());
    }

    @Test
    public void exchangeCurrency_invalidBaseCurrencyCode_shouldThrowInvalidCurrencyCodeException() {
        Money money = new Money("EURA", BigDecimal.valueOf(321));

        Assertions.assertThrows(InvalidCurrencyCodeException.class,
                () -> exchangeService
                        .exchangeCurrency(money, "USD", LocalDate.of(2022, 8, 16)));
    }

    @Test
    public void exchangeCurrency_invalidTargetCurrencyCode_shouldThrowInvalidCurrencyCodeException() {
        Money money = new Money("EUR", BigDecimal.valueOf(123));

        Assertions.assertThrows(InvalidCurrencyCodeException.class,
                () -> exchangeService
                        .exchangeCurrency(money, "USDT", LocalDate.of(2022, 8, 16)));
    }
}
