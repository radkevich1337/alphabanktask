package com.example.alphabanktask.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "rates", url = "${rates.endpoint.url:https://openexchangerates.org/api/historical}")
public interface RatesClient {

    @GetMapping("/{date}" + ".json")
    String getRates(@PathVariable("date") String date,
                    @RequestParam("app_id") String appId,
                    @RequestParam("base") String base);
}
