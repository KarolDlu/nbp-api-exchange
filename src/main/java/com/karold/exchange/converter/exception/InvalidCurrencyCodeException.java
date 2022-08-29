package com.karold.exchange.converter.exception;

public class InvalidCurrencyCodeException extends RuntimeException {
    public InvalidCurrencyCodeException() {
        super("Invalid currency code.");
    }
}
