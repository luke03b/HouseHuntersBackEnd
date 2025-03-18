package com.househuntersbackend.demo.services;

import com.househuntersbackend.demo.classes.previsionimeteo.PrevisioniMeteo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MeteoService {
    private static final String BASE_URL = "https://api.open-meteo.com/v1/forecast";

    public PrevisioniMeteo recuperaPrevisioniMeteo(double latitudine, double longitudine) {
        RestTemplate restTemplate = new RestTemplate();


        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("latitude", latitudine)
                .queryParam("longitude", longitudine)
                .queryParam("daily", "temperature_2m_max,temperature_2m_min,weathercode")
                .queryParam("hourly", "temperature_2m,weathercode")
                .queryParam("timezone", "Europe/Rome")
                .queryParam("forecast_days", "15")
                .toUriString();


        return restTemplate.getForObject(url, PrevisioniMeteo.class);
    }
}