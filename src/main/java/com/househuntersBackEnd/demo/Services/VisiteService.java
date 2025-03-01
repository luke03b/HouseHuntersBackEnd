package com.househuntersBackEnd.demo.Services;

import com.househuntersBackEnd.demo.Entities.Visite;
import com.househuntersBackEnd.demo.Enumerations.StatoVisita;
import com.househuntersBackEnd.demo.Exceptions.VisitaInAttesaEsistenteException;
import com.househuntersBackEnd.demo.Repositories.VisiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VisiteService {
    @Autowired
    private VisiteRepository visiteRepository;
    public Visite createVisite(Visite visite) throws VisitaInAttesaEsistenteException{
        boolean esisteInAttesa = visiteRepository.existsByAnnuncioAndClienteAndStatoOrStato(
                visite.getAnnuncio(), visite.getCliente(), StatoVisita.IN_ATTESA, StatoVisita.CONFERMATA
        );
        if (esisteInAttesa) {
            throw new VisitaInAttesaEsistenteException("L'utente ha già una visita in attesa per questo annuncio.");
        }
        visite.setOrarioFine(visite.getOrarioInizio().plusHours(1));
        visite.setStato(StatoVisita.IN_ATTESA);
        return visiteRepository.save(visite);
    }
    public List<Visite> getVisiteAnnuncio(UUID idAnnuncio) {
        return visiteRepository.findByAnnuncioId(idAnnuncio);
    }
    public List<Visite> getTutteVisiteCliente(UUID idCliente) {
        return visiteRepository.findByClienteId(idCliente);
    }
}
