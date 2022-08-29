package com.karold.exchange.converter.service.impl;

import com.karold.exchange.converter.repo.CurrencyRateRepo;
import com.karold.exchange.converter.service.CurrencyRateService;
import com.karold.exchange.model.CurrencyRate;
import com.karold.exchange.nbpapi.service.impl.NbpApiClientImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CurrencyRateServiceImpl implements CurrencyRateService {

    private final CurrencyRateRepo currencyRateRepo;

    private final NbpApiClientImpl nbpApiClient;

    public CurrencyRate getCurrencyRateByDate(String currencyCode, LocalDate effectiveDate) {
        return currencyRateRepo.findByCodeAndRateEffectiveDate(currencyCode, effectiveDate)
                .orElseGet(() -> getCurrentRateFromApiAndSave(currencyCode, effectiveDate));
    }

    private CurrencyRate getCurrentRateFromApiAndSave(String currencyCode, LocalDate effectiveDate) {
        CurrencyRate response = nbpApiClient.getCurrencyRate(currencyCode, effectiveDate);
        return currencyRateRepo.save(response);
    }
}
