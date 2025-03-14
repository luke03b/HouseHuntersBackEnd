package com.househuntersbackend.demo.service;

import com.househuntersbackend.demo.classes.annuncio.*;
import com.househuntersbackend.demo.entities.Annunci;
import com.househuntersbackend.demo.entities.Users;
import com.househuntersbackend.demo.entities.Visite;
import com.househuntersbackend.demo.enumerations.ClasseEnergetica;
import com.househuntersbackend.demo.enumerations.Piano;
import com.househuntersbackend.demo.enumerations.StatoAnnuncio;
import com.househuntersbackend.demo.enumerations.TipoAnnuncio;
import com.househuntersbackend.demo.repositories.AnnuncioRepository;
import com.househuntersbackend.demo.repositories.VisiteRepository;
import com.househuntersbackend.demo.services.AnnuncioService;
import com.househuntersbackend.demo.services.VisiteService;
import com.househuntersbackend.demo.utils.AnnuncioUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VisiteServiceTest {

    @Mock
    private VisiteRepository annuncioRepository;

    @InjectMocks
    private VisiteService annuncioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateVisite(Visite visita) {

    }

}