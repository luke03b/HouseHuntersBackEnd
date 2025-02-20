package com.househuntersBackEnd.demo.Controllers;

import com.househuntersBackEnd.demo.Entities.Visite;
import com.househuntersBackEnd.demo.Services.VisiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/visite")
public class VisiteController {

    @Autowired
    private VisiteService visiteService;

    @PostMapping
    public ResponseEntity<Visite> createVisite(@RequestBody Visite visite) {
        Visite newVisite = visiteService.createVisite(visite);
        return new ResponseEntity<>(newVisite, HttpStatus.CREATED);
    }
}
