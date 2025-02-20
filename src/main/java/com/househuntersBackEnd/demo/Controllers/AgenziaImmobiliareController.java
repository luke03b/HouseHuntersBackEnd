package com.househuntersBackEnd.demo.Controllers;

import com.househuntersBackEnd.demo.Entities.AgenziaImmobiliare;
import com.househuntersBackEnd.demo.Services.AgenziaImmobiliareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agenziaimmobiliare")
public class AgenziaImmobiliareController {

    @Autowired
    private AgenziaImmobiliareService agenziaImmobiliareService;

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
