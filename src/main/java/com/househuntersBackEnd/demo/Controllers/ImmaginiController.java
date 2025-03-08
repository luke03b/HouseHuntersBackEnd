package com.househuntersBackEnd.demo.Controllers;

import com.househuntersBackEnd.demo.Entities.Immagini;
import com.househuntersBackEnd.demo.Services.ImmaginiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/immagini")
public class ImmaginiController {

    private final ImmaginiService immaginiService;

    public ImmaginiController(ImmaginiService immaginiService) {
        this.immaginiService = immaginiService;
    }

    @PostMapping
    public ResponseEntity<Immagini> create(@RequestBody Immagini immagini) {
        Immagini newImmagini = immaginiService.createImmagini(immagini);
        return new ResponseEntity<>(newImmagini, HttpStatus.CREATED);
    }
}
