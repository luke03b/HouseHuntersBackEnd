package com.househuntersBackEnd.demo.Controllers;

import com.househuntersBackEnd.demo.AnnunciSpecification;
import com.househuntersBackEnd.demo.Entities.Annunci;
import com.househuntersBackEnd.demo.Enumerations.ClasseEnergetica;
import com.househuntersBackEnd.demo.Enumerations.Piano;
import com.househuntersBackEnd.demo.Services.AnnuncioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/annunci")
public class AnnuncioController {

    @Autowired
    private AnnuncioService annuncioService;

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
            @RequestParam(required = false) Boolean vicino_scuole,
            @RequestParam(required = false) Boolean vicino_parchi,
            @RequestParam(required = false) Boolean vicino_trasporti,
            @RequestParam(required = false) ClasseEnergetica classeEnergetica,
            @RequestParam(required = false) Piano piano,
            @RequestParam(required = false) String indirizzo,
            @RequestParam(required = false) Double latitudine,
            @RequestParam(required = false) Double longitudine,
            @RequestParam(required = false) Double raggioKm
    ) {
        return annuncioService.getAnnunci(prezzoMinimo, prezzoMassimo, superficieMinima,
                superficieMassima, numStanzeMinime, numStanzeMassime, garage,
                ascensore, piscina, arredo, balcone, giardino, vicino_scuole, vicino_parchi,
                vicino_trasporti, classeEnergetica, piano, indirizzo, latitudine, longitudine, raggioKm);
    }

    @GetMapping("/agente")
    public ResponseEntity<List<Annunci>> getAnnunciByAgenteSub(@RequestParam String sub) {
        List<Annunci> annunci = annuncioService.getAnnunciByAgenteSub(sub);
        return new ResponseEntity<>(annunci, HttpStatus.OK);
    }
}
