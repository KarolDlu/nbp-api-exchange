package com.karold.exchange.nbpapi.exception;

public class CurrencyRateNotFoundException extends RuntimeException {

    public CurrencyRateNotFoundException(String code) {
        super("Can't get rate for currency with code: " + code);
    }
}
