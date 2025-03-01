package com.househuntersBackEnd.demo.Controllers;

import com.househuntersBackEnd.demo.Entities.Visite;
import com.househuntersBackEnd.demo.Services.VisiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping
    public ResponseEntity<List<Visite>> getVisiteOnAnnuncio(@RequestParam UUID idAnnuncio) {
        List<Visite> visite = visiteService.getVisiteAnnuncio(idAnnuncio);
        return new ResponseEntity<>(visite, HttpStatus.OK);
    }

    @GetMapping("/cliente")
    public ResponseEntity<List<Visite>> getVisiteCliente(@RequestParam UUID idCliente) {
        List<Visite> visite = visiteService.getVisiteCliente(idCliente);
        return new ResponseEntity<>(visite, HttpStatus.OK);
    }
}
