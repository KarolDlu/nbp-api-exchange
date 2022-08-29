package com.karold.exchange;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.karold.exchange.converter.exception.InvalidCurrencyCodeException;
import com.karold.exchange.nbpapi.exception.CannotReadResponseFromNbpApiException;
import com.karold.exchange.nbpapi.exception.CurrencyRateNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CurrencyRateNotFoundException.class)
    public ResponseEntity<String> handleCurrencyRateNotFoundException(CurrencyRateNotFoundException ex) {
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<String> handleInvalidFormatException(InvalidFormatException ex) {
        return new ResponseEntity<>("Cannot deserialize value of type " + ex.getTargetType()
                + " from String " + ex.getValue(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<String> handleJsonParseException() {
        return new ResponseEntity<>("Invalid request body.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CannotReadResponseFromNbpApiException.class)
    public ResponseEntity<String> handleCannotReadResponseFromNbpApiException(CannotReadResponseFromNbpApiException ex) {
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCurrencyCodeException.class)
    public ResponseEntity<String> handleInvalidCurrencyCodeException(InvalidCurrencyCodeException ex) {
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
}
