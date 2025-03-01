package com.househuntersBackEnd.demo.Controllers;

import com.househuntersBackEnd.demo.Entities.Annunci;
import com.househuntersBackEnd.demo.Entities.Offerte;
import com.househuntersBackEnd.demo.Exceptions.OffertaInAttesaEsistenteException;
import com.househuntersBackEnd.demo.Services.OfferteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/offerte")
public class OfferteController {

    @Autowired
    private OfferteService offerteService;

    @PostMapping
    public ResponseEntity<?> createOfferte(@RequestBody Offerte offerte) {
        Offerte newOfferta;
        try {
            newOfferta = offerteService.createOfferte(offerte);
        } catch (OffertaInAttesaEsistenteException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Esiste già un'offerta in attesa per questo annuncio.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        return new ResponseEntity<>(newOfferta, HttpStatus.CREATED);
    }

    @ExceptionHandler(OffertaInAttesaEsistenteException.class)
    public ResponseEntity<Map<String, String>> handleOffertaInAttesaException(OffertaInAttesaEsistenteException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @GetMapping
    public List<Offerte> getOfferteByAnnuncio(@RequestParam UUID idAnnuncio) {
       return offerteService.getAllOfferteOnAnnuncio(idAnnuncio);
    }

    @GetMapping("/cliente")
    public List<Offerte> getOfferteConAnnuncioByCliente(@RequestParam UUID idCliente) {
        return offerteService.getOfferteConAnnuncioByClienteId(idCliente);
    }
}
