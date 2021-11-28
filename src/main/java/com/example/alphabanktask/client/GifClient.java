package com.example.alphabanktask.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "gif", url = "${gif.endpoint.url:https://api.giphy.com/v1/gifs/random}")
public interface GifClient {
    @GetMapping
    String getGif(@RequestParam("api_key") String apiKey, @RequestParam("tag") String tag);
}
