package com.househuntersbackend.demo.repository;

import com.househuntersbackend.demo.entities.AgenziaImmobiliare;
import com.househuntersbackend.demo.entities.Annunci;
import com.househuntersbackend.demo.entities.Users;
import com.househuntersbackend.demo.enumerations.*;
import com.househuntersbackend.demo.repositories.AnnuncioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AnnuncioRepositoryTest {

    @Mock
    private AnnuncioRepository annuncioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByAgenteSubTest() {
        AgenziaImmobiliare agenzia = mock(AgenziaImmobiliare.class);
        Users agente = new Users();
        agente.setId(UUID.randomUUID());
        agente.setSub("subAgenteTest");
        agente.setEmail("test@agente.com");
        agente.setNome("Mario");
        agente.setCognome("Rossi");
        agente.setTipo(UserType.AGENTE);
        agente.setAgenzia(agenzia);

        Annunci annuncio = new Annunci();
        annuncio.setAgente(agente);

        List<Annunci> annunciMock = List.of(annuncio);

        when(annuncioRepository.findByAgenteSub("subAgenteTest")).thenReturn(annunciMock);

        List<Annunci> annunciByAgenteSub = annuncioRepository.findByAgenteSub("subAgenteTest");

        assertEquals(1, annunciByAgenteSub.size());
        assertEquals(agente.getId(), annunciByAgenteSub.get(0).getAgente().getId());

        verify(annuncioRepository, times(1)).findByAgenteSub("subAgenteTest");
    }
}
