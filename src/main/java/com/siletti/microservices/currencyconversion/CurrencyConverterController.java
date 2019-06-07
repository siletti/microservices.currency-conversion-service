package com.siletti.microservices.currencyconversion;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyConverterController {

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{qty}")
    public CurrencyConversionBean retrieveConversion(@PathVariable String from,
                                                     @PathVariable String to,
                                                     @PathVariable BigDecimal qty){

        return new CurrencyConversionBean(1L, from, to, BigDecimal.ZERO, qty, BigDecimal.ZERO, 8100);
    }
}
