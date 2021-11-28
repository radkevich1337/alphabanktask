package com.example.alphabanktask.service;

import com.example.alphabanktask.client.GifClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GifService {
        private final GifClient gifClient;

        private final String gifApiKey;
        private final String[] jsonTags;

        @Autowired
        public GifService(GifClient gifClient,
                          @Value("${gif.api.key:Jwn7Zl2pON2YLEgiViqQGaBLM1ohhNdA}") String gifApiKey,
                          @Value("#{'${gif.json.tags:data,images,original}'.split(',')}") String[] jsonTags) {
                this.gifClient = gifClient;

                this.gifApiKey = gifApiKey;
                this.jsonTags = jsonTags;
        }

        public String getGif(String tag) {
                String data = gifClient.getGif(gifApiKey, tag);
                JSONObject jo = new JSONObject(data);
                for (String jsonTag : jsonTags) {
                        if (!jsonTag.trim().equals("")) jo = jo.getJSONObject(jsonTag);
                }
                String gifUrl = jo.getString("url");
                return gifUrl;
        }
}
