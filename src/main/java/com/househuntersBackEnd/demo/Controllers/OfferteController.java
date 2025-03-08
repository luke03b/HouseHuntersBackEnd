package com.househuntersBackEnd.demo.Controllers;

import com.househuntersBackEnd.demo.Entities.Offerte;
import com.househuntersBackEnd.demo.Exceptions.OffertaAccettataEsistenteException;
import com.househuntersBackEnd.demo.Exceptions.OffertaInAttesaEsistenteException;
import com.househuntersBackEnd.demo.Services.OfferteService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> createOfferte(@RequestBody Offerte offerte) {
        Offerte newOfferta;
        try {
            newOfferta = offerteService.createOfferte(offerte);
        } catch (OffertaInAttesaEsistenteException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Esiste gia' un'offerta in attesa da parte del cliente per questo annuncio.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (OffertaAccettataEsistenteException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Esiste gia' un'offerta accettata per questo annuncio.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        return new ResponseEntity<>(newOfferta, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateStatoOfferte(@RequestBody Offerte offerte, @RequestParam String stato, @RequestParam(required = false) Optional<Double> controproposta) {
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
