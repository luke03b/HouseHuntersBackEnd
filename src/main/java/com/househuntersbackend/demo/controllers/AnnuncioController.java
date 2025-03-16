package com.househuntersbackend.demo.controllers;

import com.househuntersbackend.demo.classes.annuncio.FiltriRicerca;
import com.househuntersbackend.demo.entities.Annunci;
import com.househuntersbackend.demo.exceptions.AnnuncioNonEsistenteException;
import com.househuntersbackend.demo.exceptions.AnnuncioNonValidoException;
import com.househuntersbackend.demo.services.AnnuncioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/annunci")
public class AnnuncioController {

    private final AnnuncioService annuncioService;

    public AnnuncioController(AnnuncioService annuncioService) {
        this.annuncioService = annuncioService;
    }

    @PostMapping
    public ResponseEntity<Object> createAnnuncio(@RequestBody Annunci annuncio) {
        try {
            return new ResponseEntity<>(annuncioService.createAnnuncio(annuncio), HttpStatus.CREATED);
        } catch (AnnuncioNonValidoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/filtriRicerca")
    public List<Annunci> getAnnunci(@RequestBody FiltriRicerca filtriRicerca) {
        return annuncioService.getAnnunci(filtriRicerca);
    }

    @GetMapping("/agente")
    public ResponseEntity<List<Annunci>> getAnnunciByAgenteSub(@RequestParam String sub) {
        List<Annunci> annunci = annuncioService.getAnnunciByAgenteSub(sub);
        return new ResponseEntity<>(annunci, HttpStatus.OK);
    }

    @GetMapping("/cliente/cronologia")
    public ResponseEntity<List<Annunci>> getAnnunciRecentiByClienteId(@RequestParam String idCliente) {
        List<Annunci> annunci = annuncioService.getAnnunciRecentiByClienteId(idCliente);
        return new ResponseEntity<>(annunci, HttpStatus.OK);
    }

    @GetMapping("/agente/offerte-prenotazioni")
    public ResponseEntity<List<Annunci>> getAnnunciConOffertePrenotazioniByAgenteSub(@RequestParam String sub, @RequestParam boolean offerte, @RequestParam boolean prenotazioni, @RequestParam boolean disponibili) {
        List<Annunci> annunci = annuncioService.getAnnunciConOffertePrenotazioniByAgenteSub(sub, offerte, prenotazioni, disponibili);
        return new ResponseEntity<>(annunci, HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<Object> getAnnuncioById(@RequestParam UUID idAnnuncio) {
        try{
            Annunci annuncio = annuncioService.getAnnuncioById(idAnnuncio);
            return new ResponseEntity<>(annuncio, HttpStatus.OK);
        } catch (AnnuncioNonEsistenteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
