package com.example.alphabanktask.service;

import com.example.alphabanktask.exception.BadCurrencyNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static j2html.TagCreator.*;

@Service
public class ExchangeRatesService {
    private final RatesService ratesService;
    private final GifService gifService;

    private final String tagMore;
    private final String tagLess;

    @Autowired
    public ExchangeRatesService(RatesService ratesService, GifService gifService,
                                @Value("${gif.tag.more:rich}") String tagMore,
                                @Value("${gif.tag.less:broke}") String tagLess) {
        this.ratesService = ratesService;
        this.gifService = gifService;

        this.tagMore = tagMore;
        this.tagLess = tagLess;
    }

    public String getGif(String currency) throws BadCurrencyNameException {
        String tag = ratesService.isGrowth(currency) ? tagMore : tagLess;
        String gifUrl = gifService.getGif(tag);
        String res = body(
                img().withSrc(gifUrl)
        ).render();
        return res;
    }
}
