package com.househuntersbackend.demo.controllers;

import com.househuntersbackend.demo.entities.Offerte;
import com.househuntersbackend.demo.exceptions.OffertaAccettataEsistenteException;
import com.househuntersbackend.demo.exceptions.OffertaInAttesaEsistenteException;
import com.househuntersbackend.demo.services.OfferteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/offerte")
public class OfferteController {

    private final OfferteService offerteService;

    public OfferteController(OfferteService offerteService) {
        this.offerteService = offerteService;
    }

    @PostMapping
    public ResponseEntity<Object> createOfferte(@RequestBody Offerte offerte) {
        try {
            Offerte newOfferta = offerteService.createOfferte(offerte);
            return ResponseEntity.status(HttpStatus.CREATED).body(newOfferta);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    private ResponseEntity<Object> handleException(Exception e) {
        Map<String, String> errorResponse = new HashMap<>();

        if (e instanceof OffertaInAttesaEsistenteException) {
            errorResponse.put("offerta esistente", "Esiste già un'offerta in attesa da parte del cliente per questo annuncio.");
        } else if (e instanceof OffertaAccettataEsistenteException) {
            errorResponse.put("annuncio venduto", "Esiste già un'offerta accettata per questo annuncio.");
        } else {
            errorResponse.put("errore", "Si è verificato un errore imprevisto.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateStatoOfferte(@RequestBody Offerte offerte, @RequestParam String stato, @RequestParam(required = false) Optional<Double> controproposta) {
        if(controproposta.isPresent()){
            offerteService.updateStatoOfferteControproposta(offerte, controproposta);
        } else {
            offerteService.updateStatoOfferte(offerte, stato);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public List<Offerte> getOfferteByAnnuncio(@RequestParam UUID idAnnuncio) {
       return offerteService.getAllOfferteOnAnnuncio(idAnnuncio);
    }

    @GetMapping("/cliente")
    public List<Offerte> getOfferteConAnnuncioByCliente(@RequestParam UUID idCliente) {
        return offerteService.getOfferteConAnnuncioByClienteId(idCliente);
    }

    @GetMapping("/stato")
    public List<Offerte> getOfferteSuAnnuncioByStato(@RequestParam UUID idAnnuncio, @RequestParam String stato) {
        return offerteService.getOfferteSuAnnuncioByStato(idAnnuncio, stato);
    }
}
