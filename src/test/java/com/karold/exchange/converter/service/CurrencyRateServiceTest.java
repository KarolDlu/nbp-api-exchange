package com.karold.exchange.converter.service;

import com.karold.exchange.converter.repo.CurrencyRateRepo;
import com.karold.exchange.converter.service.impl.CurrencyRateServiceImpl;
import com.karold.exchange.model.CurrencyRate;
import com.karold.exchange.model.Rate;
import com.karold.exchange.nbpapi.service.impl.NbpApiClientImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyRateServiceTest {

    @Mock
    private CurrencyRateRepo currencyRateRepo;

    @Mock
    private NbpApiClientImpl nbpApiClient;

    @InjectMocks
    private CurrencyRateServiceImpl currencyRateService;

    @Test
    public void currencyRateService_newCurrencyRateGiven_shouldGetAndSaveCurrencyRateFromApi() {
        when(currencyRateRepo.findByCodeAndRateEffectiveDate(any(String.class), any(LocalDate.class)))
                .thenReturn(Optional.empty());
        when(nbpApiClient.getCurrencyRate(any(String.class), any(LocalDate.class)))
                .thenAnswer(args -> {
                    Rate rate = new Rate(args.getArgument(1), BigDecimal.valueOf(4.34));
                    return new CurrencyRate(args.getArgument(0), rate);
                });
        when(currencyRateRepo.save(any(CurrencyRate.class))).thenAnswer(args -> args.getArgument(0));
        String code = "EUR";
        LocalDate date = LocalDate.of(2022, 8, 12);

        CurrencyRate currencyRate = currencyRateService
                .getCurrencyRateByDate(code, date);

        verify(nbpApiClient, times(1))
                .getCurrencyRate(code, date);
        verify(currencyRateRepo, times(1)).save(any(CurrencyRate.class));
        Assertions.assertEquals(code, currencyRate.getCode());
        Assertions.assertEquals(BigDecimal.valueOf(4.34), currencyRate.getRateMid());
        Assertions.assertEquals(date, currencyRate.getRate().getEffectiveDate());
    }

    @Test
    public void currencyRateService_whenCurrencyRateSaved_shouldReturnCurrencyRateFromDatabase() {
        when(currencyRateRepo.findByCodeAndRateEffectiveDate(any(String.class), any(LocalDate.class)))
                .thenAnswer(args -> Optional.of(new CurrencyRate(args.getArgument(0),
                        new Rate(args.getArgument(1), BigDecimal.valueOf(3.26)))));
        String code = "USD";
        LocalDate date = LocalDate.of(2022, 8, 16);

        CurrencyRate currencyRate = currencyRateService
                .getCurrencyRateByDate(code, date);

        verify(nbpApiClient, times(0))
                .getCurrencyRate(code, date);
        Assertions.assertEquals(code, currencyRate.getCode());
        Assertions.assertEquals(BigDecimal.valueOf(3.26), currencyRate.getRateMid());
        Assertions.assertEquals(date, currencyRate.getRate().getEffectiveDate());
    }
}
