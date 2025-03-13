package com.househuntersbackend.demo.controllers;

import com.househuntersbackend.demo.entities.Visite;
import com.househuntersbackend.demo.exceptions.DataNonValidaException;
import com.househuntersbackend.demo.exceptions.OffertaAccettataEsistenteException;
import com.househuntersbackend.demo.exceptions.VisitaInAttesaEsistenteException;
import com.househuntersbackend.demo.exceptions.VisitaInAttesaPerFasciaOrariaEsistenteException;
import com.househuntersbackend.demo.services.VisiteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/visite")
public class VisiteController {

    private final VisiteService visiteService;

    public VisiteController(VisiteService visiteService) {
        this.visiteService = visiteService;
    }

    @PostMapping
    public ResponseEntity<Object> createVisite(@RequestBody Visite visite) {
        Visite newVisita;
        try {
           newVisita = visiteService.createVisite(visite);
        } catch (VisitaInAttesaEsistenteException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("visita in attesa esistente", "Esiste gia' una visita in attesa chiesta dal cliente per questo annuncio.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (VisitaInAttesaPerFasciaOrariaEsistenteException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("visita in attesa o accettata per quella fascia oraria", "Esiste gia' una visita in attesa o accettata per questo annuncio nella fascia oraria scelta");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (OffertaAccettataEsistenteException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("annuncio venduto", "Esiste gia' un'offerta accettata per questo annuncio, quindi non e' possibile prenotare visite");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (DataNonValidaException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("Data non valida", "Non è possibile prenotare visite per il giorno stesso, scegliere un giorno diverso e riprovare");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        return new ResponseEntity<>(newVisita, HttpStatus.CREATED);
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
