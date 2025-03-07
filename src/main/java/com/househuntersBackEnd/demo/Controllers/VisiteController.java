package com.househuntersBackEnd.demo.Controllers;

import com.househuntersBackEnd.demo.Entities.Offerte;
import com.househuntersBackEnd.demo.Entities.Visite;
import com.househuntersBackEnd.demo.Exceptions.OffertaAccettataEsistenteException;
import com.househuntersBackEnd.demo.Exceptions.VisitaInAttesaEsistenteException;
import com.househuntersBackEnd.demo.Exceptions.VisitaInAttesaPerFasciaOrariaEsistenteException;
import com.househuntersBackEnd.demo.Services.VisiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/visite")
public class VisiteController {

    @Autowired
    private VisiteService visiteService;

    @PostMapping
    public ResponseEntity<?> createVisite(@RequestBody Visite visite) {
        Visite newVisita;
        try {
           newVisita = visiteService.createVisite(visite);
        } catch (VisitaInAttesaEsistenteException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Esiste già una visita in attesa per questo annuncio.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (VisitaInAttesaPerFasciaOrariaEsistenteException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Esiste già una visita in attesa o accettata per questo annuncio nella fascia oraria scelta");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (OffertaAccettataEsistenteException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "esiste già un'offerta accettata per questo annuncio, quindi non è possibile prenotare visite");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        return new ResponseEntity<>(newVisita, HttpStatus.CREATED);
    }

    @ExceptionHandler(VisitaInAttesaEsistenteException.class)
    public ResponseEntity<Map<String, String>> handleVisitaInAttesaException(VisitaInAttesaEsistenteException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
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
    public ResponseEntity<?> updateStatoOfferte(@RequestBody Visite visite, @RequestParam String stato) {
        visiteService.updateStatoVisite(visite, stato);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
