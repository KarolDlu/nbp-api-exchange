package com.karold.exchange.nbpapi.service.impl;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karold.exchange.model.CurrencyRate;
import com.karold.exchange.nbpapi.CurrencyRatesResponse;
import com.karold.exchange.nbpapi.exception.CannotReadResponseFromNbpApiException;
import com.karold.exchange.nbpapi.exception.CurrencyRateNotFoundException;
import com.karold.exchange.nbpapi.service.NbpApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

@Service
@PropertySource(value = "classpath:nbpapi.properties")
public class NbpApiClientImpl implements NbpApiClient {

    private final String NBP_API_URI;

    private final String TABLE_A_RATES_ENDPOINT;

    private final String BASIC_REQUEST_PARAMS;

    private final ObjectMapper objectMapper;

    @Autowired
    public NbpApiClientImpl(@Value("${NBP_API_URI}") String NBP_API_URI,
                            @Value("${TABLE_A_RATES_ENDPOINT}") String TABLE_A_RATES_ENDPOINT,
                            @Value("${BASIC_REQUEST_PARAMS}") String BASIC_REQUEST_PARAMS,
                            ObjectMapper objectMapper) {

        this.NBP_API_URI = NBP_API_URI;
        this.TABLE_A_RATES_ENDPOINT = TABLE_A_RATES_ENDPOINT;
        this.BASIC_REQUEST_PARAMS = BASIC_REQUEST_PARAMS;
        this.objectMapper = objectMapper;
    }

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
