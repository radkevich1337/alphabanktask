package com.example.alphabanktask.controller;

import com.example.alphabanktask.exception.BadCurrencyNameException;
import com.example.alphabanktask.service.ExchangeRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;


@RestController
@Validated
public class ExchangeRatesController {
    private final ExchangeRatesService exchangeRatesService;

    @Autowired
    public ExchangeRatesController(ExchangeRatesService exchangeRatesService) {
        this.exchangeRatesService = exchangeRatesService;
    }

    @GetMapping("/rates")
    public ResponseEntity getRates(@NotBlank @RequestParam("base") String currency) throws BadCurrencyNameException {
        return new ResponseEntity(exchangeRatesService.getGif(currency), HttpStatus.OK);
    }

    @ExceptionHandler(BadCurrencyNameException.class)
    public ResponseEntity handlerCurrencyNameException(BadCurrencyNameException ex) {
        return new ResponseEntity(ex.getName() + " - bad base code", HttpStatus.BAD_REQUEST);
    }
}
