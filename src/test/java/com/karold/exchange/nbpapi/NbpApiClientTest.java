package com.karold.exchange.nbpapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karold.exchange.model.CurrencyRate;
import com.karold.exchange.model.Rate;
import com.karold.exchange.nbpapi.service.impl.NbpApiClientImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NbpApiClientTest {
    private ObjectMapper objectMapper = mock(ObjectMapper.class);

    private NbpApiClientImpl nbpApiClient = new NbpApiClientImpl("http://api.nbp.pl/api",
            "/exchangerates/rates/A/", "?format=json", objectMapper);

    @Test
    void getCurrencyRateTest_validDataGiven_ShouldReturnCurrencyRateFromApi() throws IOException {
        ArrayList<Rate> rates = new ArrayList<>();
        LocalDate date = LocalDate.of(2022, 8, 14);
        rates.add(new Rate(date, BigDecimal.valueOf(4.58)));
        when(objectMapper.readValue(any(URL.class), eq(CurrencyRatesResponse.class)))
                .thenReturn(new CurrencyRatesResponse("EUR", rates));

        CurrencyRate response = nbpApiClient
                .getCurrencyRate("EUR", LocalDate.of(2022, 8, 16));

        Assertions.assertEquals("EUR", response.getCode());
        Assertions.assertEquals(BigDecimal.valueOf(4.58), response.getRateMid());
        Assertions.assertEquals(date, response.getRate().getEffectiveDate());
    }
}
