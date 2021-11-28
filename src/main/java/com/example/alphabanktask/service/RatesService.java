package com.example.alphabanktask.service;

import com.example.alphabanktask.client.RatesClient;
import com.example.alphabanktask.exception.BadCurrencyNameException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
public class RatesService {
    private final Map<String, JSONObject> currencies;
    private final RatesClient ratesClient;

    private final String ratesApiKey;
    private final String ratesBase;
    private final String[] jsonTags;

    @Autowired
    public RatesService(RatesClient ratesClient,
                        @Value("${rates.api.key:da863c29ee7a4188b7162a3a381260c9}") String ratesApiKey,
                        @Value("${rates.base:usd}") String ratesBase,
                        @Value("#{'${rates.json.tags:}'.split(',')}") String[] jsonTags) {
        this.ratesClient = ratesClient;

        this.ratesApiKey = ratesApiKey;
        this.ratesBase = ratesBase;
        this.jsonTags = jsonTags;

        this.currencies = new HashMap<>();
    }

    public boolean isGrowth(String currency) throws BadCurrencyNameException {
        Instant now = Instant.now();
        Instant yesterday = now.minus(1, ChronoUnit.DAYS);

        String dateNow = LocalDateTime.ofInstant(now, ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE);
        String dateYesterday = LocalDateTime.ofInstant(yesterday, ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE);

        if (!currencies.containsKey(dateNow)) updateRates(dateNow);
        if (!currencies.containsKey(dateYesterday)) updateRates(dateYesterday);

        try {
            return currencies.get(dateNow).getDouble(currency.toUpperCase()) >= currencies.get(dateYesterday).getDouble(currency.toUpperCase());
        } catch (JSONException exception) {
            throw new BadCurrencyNameException(currency);
        }
    }

    private void updateRates(String date) {
        String data = ratesClient.getRates(date, ratesApiKey, ratesBase);
        JSONObject jo = new JSONObject(data);
        for (String jsonTag : jsonTags) {
            if (!jsonTag.trim().equals("")) jo = jo.getJSONObject(jsonTag);
        }
        currencies.put(date, jo.getJSONObject("rates"));
    }
}
