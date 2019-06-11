package com.siletti.microservices.currencyconversion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConverterController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CurrencyExchangeServiceProxy proxy;

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{qty}")
    public CurrencyConversionBean retrieveConversion(@PathVariable String from,
                                                     @PathVariable String to,
                                                     @PathVariable BigDecimal qty){

        Map<String, String> uriVar = new HashMap<>();
        uriVar.put("from", from);
        uriVar.put("to", to);

        ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
                "http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversionBean.class,
                uriVar);

        CurrencyConversionBean conversionBean = responseEntity.getBody();

        return new CurrencyConversionBean(conversionBean.getId(), from, to,
                conversionBean.getConversion(),
                qty,
               qty.multiply(conversionBean.getConversion()),
                conversionBean.getPort());

    }

    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{qty}")
    public CurrencyConversionBean convert(@PathVariable String from,
                                                     @PathVariable String to,
                                                     @PathVariable BigDecimal qty){


        CurrencyConversionBean conversionBean = proxy.retrieveExchangeValue(from,to);

        logger.info("{}", conversionBean);

        return new CurrencyConversionBean(conversionBean.getId(), from, to,
                conversionBean.getConversion(),
                qty,
               qty.multiply(conversionBean.getConversion()),
                conversionBean.getPort());

    }
}
