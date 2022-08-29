package com.karold.exchange.nbpapi.service.impl;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karold.exchange.model.CurrencyRate;
import com.karold.exchange.nbpapi.CurrencyRatesResponse;
import com.karold.exchange.nbpapi.exception.CannotReadResponseFromNbpApiException;
import com.karold.exchange.nbpapi.exception.CurrencyRateNotFoundException;
import com.karold.exchange.nbpapi.service.NbpApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@PropertySource(value = "classpath:nbpapi.properties")
public class NbpApiClientImpl implements NbpApiClient {

    @Value("${NBP_API_URI}")
    private String NBP_API_URI;

    @Value("${TABLE_A_RATES_ENDPOINT}")
    private String TABLE_A_RATES_ENDPOINT;

    @Value("${BASIC_REQUEST_PARAMS}")
    private String BASIC_REQUEST_PARAMS;

    private final ObjectMapper objectMapper;

    public CurrencyRate getCurrencyRate(String currencyCode, LocalDate date) {
        try {
            URL url = new URL(NBP_API_URI + TABLE_A_RATES_ENDPOINT + currencyCode + "/" + date
                    + BASIC_REQUEST_PARAMS);
            CurrencyRatesResponse response = objectMapper
                    .readValue(url, CurrencyRatesResponse.class);
            return response.toCurrencyRate();
        } catch (DatabindException ex) {
            throw new CurrencyRateNotFoundException(currencyCode);
        } catch (IOException ex) {
            throw new CannotReadResponseFromNbpApiException(ex);
        }
    }
}
