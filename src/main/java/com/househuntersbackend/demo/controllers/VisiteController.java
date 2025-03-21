package com.househuntersbackend.demo.controllers;

import com.househuntersbackend.demo.entities.Visite;
import com.househuntersbackend.demo.exceptions.*;
import com.househuntersbackend.demo.services.EmailService;
import com.househuntersbackend.demo.services.VisiteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/visite")
public class VisiteController {

    private final VisiteService visiteService;
    private final EmailService emailService;

    public VisiteController(VisiteService visiteService, EmailService emailService) {
        this.visiteService = visiteService;
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<Object> createVisite(@RequestBody Visite visite) {
        try {
            Visite newVisita = visiteService.createVisite(visite);

            String corpoMail = emailService.creaCorpoMail(
                    newVisita.getAnnuncio().getAgente().getNome(),
                    newVisita.getAnnuncio().getTipoAnnuncio(),
                    newVisita.getAnnuncio().getPrezzo(),
                    newVisita.getAnnuncio().getIndirizzo()
            );

            emailService.sendEmail(newVisita.getAnnuncio().getAgente().getEmail(), "Nuova prenotazione al tuo annuncio su HouseHunters!", corpoMail);

            return new ResponseEntity<>(newVisita, HttpStatus.CREATED);
        } catch (VisitaNonValidaException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("visita non valida", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<List<Visite>> getVisiteOnAnnuncio(@RequestParam UUID idAnnuncio) {
        List<Visite> visite = visiteService.getVisiteAnnuncio(idAnnuncio);
        return new ResponseEntity<>(visite, HttpStatus.OK);
    }

    @GetMapping("/cliente")
    public ResponseEntity<List<Visite>> getTutteVisiteCliente(@RequestParam UUID idCliente) {
        List<Visite> visite = visiteService.getTutteVisiteCliente(idCliente);
        return new ResponseEntity<>(visite, HttpStatus.OK);
    }

    @GetMapping("/stato")
    public ResponseEntity<List<Visite>> getVisiteByStatoOnAnnuncio(@RequestParam UUID idAnnuncio, @RequestParam String stato) {
        List<Visite> visite = visiteService.getVisiteByStatoOnAnnuncio(idAnnuncio, stato);
        return new ResponseEntity<>(visite, HttpStatus.OK);
    }

    @GetMapping("/agente/stato")
    public ResponseEntity<List<Visite>> getTutteVisiteConStatoByAgente(@RequestParam String stato, @RequestParam String subAgente) {
        List<Visite> visite = visiteService.getTutteVisiteConStatoByAgente(stato, subAgente);
        return new ResponseEntity<>(visite, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateStatoOfferte(@RequestBody Visite visite, @RequestParam String stato) {
        visiteService.updateStatoVisite(visite, stato);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
