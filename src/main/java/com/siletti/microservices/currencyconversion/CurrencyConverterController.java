package com.siletti.microservices.currencyconversion;

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
        //System.out.println("................. body: "+conversionBean.toString());

        return new CurrencyConversionBean(conversionBean.getId(), from, to,
                conversionBean.getConversion(),
                qty,
               qty.multiply(conversionBean.getConversion()),
                conversionBean.getPort());

    }
}
