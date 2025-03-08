package com.househuntersbackend.demo.controllers;

import com.househuntersbackend.demo.entities.Annunci;
import com.househuntersbackend.demo.entities.FiltriRicerca;
import com.househuntersbackend.demo.enumerations.ClasseEnergetica;
import com.househuntersbackend.demo.enumerations.Piano;
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
    public ResponseEntity<Annunci> createAnnuncio(@RequestBody Annunci annuncio) {
        Annunci newAnnuncio = annuncioService.createAnnuncio(annuncio);
        return new ResponseEntity<>(newAnnuncio, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Annunci> getAnnunci(
            @RequestParam(required = false) Double prezzoMinimo,
            @RequestParam(required = false) Double prezzoMassimo,
            @RequestParam(required = false) Integer superficieMinima,
            @RequestParam(required = false) Integer superficieMassima,
            @RequestParam(required = false) Integer numStanzeMinime,
            @RequestParam(required = false) Integer numStanzeMassime,
            @RequestParam(required = false) Boolean garage,
            @RequestParam(required = false) Boolean ascensore,
            @RequestParam(required = false) Boolean piscina,
            @RequestParam(required = false) Boolean arredo,
            @RequestParam(required = false) Boolean balcone,
            @RequestParam(required = false) Boolean giardino,
            @RequestParam(required = false) Boolean vicinoScuole,
            @RequestParam(required = false) Boolean vicinoParchi,
            @RequestParam(required = false) Boolean vicinoTrasporti,
            @RequestParam(required = false) ClasseEnergetica classeEnergetica,
            @RequestParam(required = false) Piano piano,
            @RequestParam(required = false) String indirizzo,
            @RequestParam(required = false) Double latitudine,
            @RequestParam(required = false) Double longitudine,
            @RequestParam(required = false) Double raggioKm,
            @RequestParam(required = false) String tipoAnnuncio
    ) {
        FiltriRicerca filtriRicerca = new FiltriRicerca(
                tipoAnnuncio,
                latitudine,
                longitudine,
                raggioKm,
                prezzoMinimo,
                prezzoMassimo,
                superficieMinima,
                superficieMassima,
                numStanzeMinime,
                numStanzeMassime,
                garage,
                ascensore,
                piscina,
                arredo,
                balcone,
                giardino,
                vicinoScuole,
                vicinoParchi,
                vicinoTrasporti,
                classeEnergetica,
                piano
        );
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
    public ResponseEntity<Annunci> getAnnuncioById(@RequestParam UUID idAnnuncio) {
        Annunci annuncio = annuncioService.getAnnuncioById(idAnnuncio);
        return new ResponseEntity<>(annuncio, HttpStatus.OK);
    }

}
