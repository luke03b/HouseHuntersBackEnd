package com.househuntersBackEnd.demo.Controllers;

import com.househuntersBackEnd.demo.Entities.Offerte;
import com.househuntersBackEnd.demo.Services.OfferteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/offerte")
public class OfferteController {

    @Autowired
    private OfferteService offerteService;

    @PostMapping
    public ResponseEntity<Offerte> createOfferte(@RequestBody Offerte offerte) {
        Offerte newOfferte = offerteService.createOfferte(offerte);
        return new ResponseEntity<>(newOfferte, HttpStatus.CREATED);
    }
}
