package com.karold.exchange.converter.controller;

import com.karold.exchange.converter.service.ExchangeService;
import com.karold.exchange.model.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchange")
class ExchangeController {

    private final ExchangeService exchangeService;

    @Autowired
    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @PostMapping
    public ResponseEntity<Money> exchangeCurrency(@RequestBody ExchangeRequest exchangeRequest) {
        return ResponseEntity.ok(exchangeService.exchangeCurrency(exchangeRequest.getValue(),
                exchangeRequest.getTargetCurrencyCode(), exchangeRequest.getEffectiveDate()));
    }
}
