package com.househuntersbackend.demo.controllers;

import com.househuntersbackend.demo.entities.Cronologia;
import com.househuntersbackend.demo.services.CronologiaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cronologia")
public class CronologiaController {

    private final CronologiaService cronologiaService;

    public CronologiaController(CronologiaService cronologiaService) {
        this.cronologiaService = cronologiaService;
    }

    @PostMapping
    public ResponseEntity<Cronologia> createCronologia(@RequestBody Cronologia cronologia) {
        Cronologia newCronologia = cronologiaService.createCronologia(cronologia);
        return new ResponseEntity<>(newCronologia, HttpStatus.CREATED);
    }
}
