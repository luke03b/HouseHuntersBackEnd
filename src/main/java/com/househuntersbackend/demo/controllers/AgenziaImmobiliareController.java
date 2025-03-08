package com.househuntersbackend.demo.controllers;

import com.househuntersbackend.demo.entities.AgenziaImmobiliare;
import com.househuntersbackend.demo.services.AgenziaImmobiliareService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agenziaimmobiliare")
public class AgenziaImmobiliareController {

    private final AgenziaImmobiliareService agenziaImmobiliareService;

    public AgenziaImmobiliareController(AgenziaImmobiliareService agenziaImmobiliareService) {
        this.agenziaImmobiliareService = agenziaImmobiliareService;
    }

    @PostMapping
    public ResponseEntity<AgenziaImmobiliare> createAgenziaImmobiliare(@RequestBody AgenziaImmobiliare agenzia) {
        AgenziaImmobiliare newAgenziaImmobiliare = agenziaImmobiliareService.createAgenziaImmobiliare(agenzia);
        return new ResponseEntity<>(newAgenziaImmobiliare, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AgenziaImmobiliare>> getAllAgenziaImmobiliare() {
        List<AgenziaImmobiliare> agenzie = agenziaImmobiliareService.getAllAgenziaImmobiliare();
        return new ResponseEntity<>(agenzie, HttpStatus.OK);
    }
}
