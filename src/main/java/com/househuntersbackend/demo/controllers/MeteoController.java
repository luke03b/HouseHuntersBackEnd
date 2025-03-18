package com.househuntersbackend.demo.controllers;

import com.househuntersbackend.demo.classes.previsionimeteo.PrevisioniMeteo;
import com.househuntersbackend.demo.services.MeteoService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meteo")
public class MeteoController {

    private final MeteoService previsioniMeteoService;

    public MeteoController(MeteoService previsioniMeteoService) {
        this.previsioniMeteoService = previsioniMeteoService;
    }

    @GetMapping
    public PrevisioniMeteo getPrevisioniMeteo(@RequestParam double latitudine, @RequestParam double longitudine) {
        return previsioniMeteoService.recuperaPrevisioniMeteo(latitudine, longitudine);
    }
}