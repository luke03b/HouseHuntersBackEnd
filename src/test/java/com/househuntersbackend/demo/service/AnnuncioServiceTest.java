package com.househuntersbackend.demo.service;

import com.househuntersbackend.demo.entities.Annunci;
import com.househuntersbackend.demo.entities.Users;
import com.househuntersbackend.demo.enumerations.ClasseEnergetica;
import com.househuntersbackend.demo.enumerations.Piano;
import com.househuntersbackend.demo.enumerations.StatoAnnuncio;
import com.househuntersbackend.demo.enumerations.TipoAnnuncio;
import com.househuntersbackend.demo.repositories.AnnuncioRepository;
import com.househuntersbackend.demo.services.AnnuncioService;
import com.househuntersbackend.demo.utils.AnnuncioUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnnuncioServiceTest {

    @Mock
    private AnnuncioRepository annuncioRepository;

    @InjectMocks
    private AnnuncioService annuncioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreaAnnuncio() {
        UUID annuncioId = UUID.randomUUID();
        Annunci annuncio = new Annunci();
        Users agente = mock(Users.class);
        AnnuncioUtils annuncioUtils = new AnnuncioUtils();

        annuncio.setId(annuncioId);
        annuncio.setTipoAnnuncio(TipoAnnuncio.VENDITA);
        annuncio.setStato(StatoAnnuncio.DISPONIBILE);
        annuncio.setPrezzo(100000);
        annuncio.setSuperficie(100);
        annuncio.setNumStanze(5);
        annuncio.setGarage(false);
        annuncio.setAscensore(false);
        annuncio.setPiscina(false);
        annuncio.setArredo(false);
        annuncio.setBalcone(false);
        annuncio.setGiardino(false);
        annuncio.setClasseEnergetica(ClasseEnergetica.D);
        annuncio.setPiano(Piano.INTERMEDIO);
        annuncio.setNumeroPiano(3);
        annuncio.setAgente(agente);
        annuncio.setIndirizzo("Via Tommaso De Vivo, 10, Orta di Atella, Caserta");
        annuncio.setLatitudine(40.970556599999995);
        annuncio.setLongitudine(14.266755199999999);
        annuncio.setDescrizione("test");
        annuncioUtils.setVicinanze(annuncio);

        when(annuncioRepository.save(annuncio)).thenReturn(annuncio);

        Annunci annuncioSalvato = annuncioService.createAnnuncio(annuncio);

        assertEquals(annuncio.getId(), annuncioSalvato.getId());
        assertEquals(TipoAnnuncio.VENDITA, annuncioSalvato.getTipoAnnuncio());
        assertEquals(StatoAnnuncio.DISPONIBILE, annuncioSalvato.getStato());
        assertEquals(100000, annuncioSalvato.getPrezzo());
        assertEquals(100, annuncioSalvato.getSuperficie());
        assertEquals(5, annuncioSalvato.getNumStanze());
        assertFalse(annuncioSalvato.isGarage());
        assertFalse(annuncioSalvato.isAscensore());
        assertFalse(annuncioSalvato.isPiscina());
        assertFalse(annuncioSalvato.isArredo());
        assertFalse(annuncioSalvato.isBalcone());
        assertFalse(annuncioSalvato.isGiardino());
        assertFalse(annuncioSalvato.isVicinoScuole());
        assertTrue(annuncioSalvato.isVicinoParchi());
        assertFalse(annuncioSalvato.isVicinoTrasporti());
        assertEquals(ClasseEnergetica.D, annuncioSalvato.getClasseEnergetica());
        assertEquals(Piano.INTERMEDIO, annuncioSalvato.getPiano());
        assertEquals(3, annuncioSalvato.getNumeroPiano());
        assertEquals(annuncio.getDataCreazione(), annuncioSalvato.getDataCreazione());
        assertEquals(agente, annuncioSalvato.getAgente());
        assertEquals("Via Tommaso De Vivo, 10, Orta di Atella, Caserta", annuncioSalvato.getIndirizzo());
        assertEquals(40.970556599999995, annuncioSalvato.getLatitudine(), 0.001);
        assertEquals(14.266755199999999, annuncioSalvato.getLongitudine(), 0.001);
        assertEquals("test", annuncioSalvato.getDescrizione());

        verify(annuncioRepository, times(1)).save(annuncio);

    }

    @Test
    void testTrovaAnnuncioById() {
        UUID annuncioId = UUID.randomUUID();
        Annunci annuncio = new Annunci();
        annuncio.setId(annuncioId);

        when(annuncioRepository.findById(annuncioId)).thenReturn(Optional.of(annuncio));

        Optional<Annunci> result = Optional.ofNullable(annuncioService.getAnnuncioById(annuncioId));

        assertTrue(result.isPresent());
        assertEquals(annuncioId, result.get().getId());
        verify(annuncioRepository, times(1)).findById(annuncioId);
    }
}
