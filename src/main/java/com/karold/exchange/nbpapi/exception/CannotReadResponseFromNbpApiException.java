package com.karold.exchange.nbpapi.exception;

public class CannotReadResponseFromNbpApiException extends RuntimeException {

    public CannotReadResponseFromNbpApiException(Exception ex) {
        super("Error occurred: can't get valid response from api. Exception: " + ex.getMessage());
    }
}
