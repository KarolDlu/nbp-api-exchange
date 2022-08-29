package com.karold.exchange.nbpapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.karold.exchange.model.CurrencyRate;
import com.karold.exchange.nbpapi.exception.CurrencyRateNotFoundException;
import com.karold.exchange.model.Rate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CurrencyRatesResponse {

    @JsonProperty(required = true)
    private String code;

    private List<Rate> rates;

    public CurrencyRate toCurrencyRate() {
        Rate rate = rates.stream()
                .findFirst()
                .orElseThrow(() -> new CurrencyRateNotFoundException(code));
        return new CurrencyRate(code, rate);
    }
}
