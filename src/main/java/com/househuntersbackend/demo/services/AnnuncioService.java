package com.househuntersbackend.demo.services;

import com.househuntersbackend.demo.AnnunciSpecification;
import com.househuntersbackend.demo.entities.Annunci;
import com.househuntersbackend.demo.entities.FiltriRicerca;
import com.househuntersbackend.demo.enumerations.StatoAnnuncio;
import com.househuntersbackend.demo.repositories.AnnuncioRepository;
import com.househuntersbackend.demo.utils.AnnuncioUtils;
import com.househuntersbackend.demo.utils.GeoUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AnnuncioService {

    private final AnnuncioRepository annuncioRepository;
    private final AnnuncioUtils annuncioUtils = new AnnuncioUtils();

    public AnnuncioService(AnnuncioRepository annuncioRepository) {
        this.annuncioRepository = annuncioRepository;
    }


    public Annunci createAnnuncio(Annunci annuncio) {
        annuncio.setDataCreazione(LocalDateTime.now());

        annuncioUtils.setVicinanze(annuncio);

        return annuncioRepository.save(annuncio);
    }


    public List<Annunci> getAnnunci(FiltriRicerca filtriRicerca) {
        Specification<Annunci> spec = Specification.where(null);

        spec = getAnnunciPrezzoMaxMin(filtriRicerca, spec);
        spec = getAnnunciSuperficieMaxMin(filtriRicerca, spec);
        spec = getAnnunciNumeroStanzeMaxMin(filtriRicerca, spec);
        spec = getAnnunciCaratteristiche(filtriRicerca, spec);
        spec = getAnnunciVicinanze(filtriRicerca, spec);
        spec = getAnnunciOthers(filtriRicerca, spec);
        spec = spec.and(AnnunciSpecification.hasStatoAnnuncio(StatoAnnuncio.DISPONIBILE.toString()));



        if (filtriRicerca.getLatitudine() != null && filtriRicerca.getLongitudine() != null && filtriRicerca.getRaggioKm() != null) {
            double[] deltas = GeoUtils.kmToDegrees(filtriRicerca.getLatitudine(), filtriRicerca.getRaggioKm());
            double minLat = filtriRicerca.getLatitudine() - deltas[0];
            double maxLat = filtriRicerca.getLatitudine() + deltas[0];
            double minLon = filtriRicerca.getLongitudine() - deltas[1];
            double maxLon = filtriRicerca.getLongitudine() + deltas[1];

            spec = spec.and(AnnunciSpecification.hasLatitudineBetween(minLat, maxLat))
                    .and(AnnunciSpecification.hasLongitudineBetween(minLon, maxLon));
        }

        return annuncioRepository.findAll(spec);
    }

    private static Specification<Annunci> getAnnunciOthers(FiltriRicerca filtriRicerca, Specification<Annunci> spec) {
        if (filtriRicerca.getClasseEnergetica() != null) spec = spec.and(AnnunciSpecification.hasClasseEnergetica(filtriRicerca.getClasseEnergetica()));
        if (filtriRicerca.getPiano() != null) spec = spec.and(AnnunciSpecification.hasPiano(filtriRicerca.getPiano()));
        if (filtriRicerca.getTipoAnnuncio() != null) spec = spec.and(AnnunciSpecification.hasTipoAnnuncio(filtriRicerca.getTipoAnnuncio()));
        return spec;
    }

    private static Specification<Annunci> getAnnunciVicinanze(FiltriRicerca filtriRicerca, Specification<Annunci> spec) {
        if (filtriRicerca.getVicinoScuole() != null) spec = spec.and(AnnunciSpecification.hasVicinoScuole(filtriRicerca.getVicinoScuole()));
        if (filtriRicerca.getVicinoParchi() != null) spec = spec.and(AnnunciSpecification.hasVicinoParchi(filtriRicerca.getVicinoParchi()));
        if (filtriRicerca.getVicinoTrasporti() != null) spec = spec.and(AnnunciSpecification.hasVicinoTrasporti(filtriRicerca.getVicinoTrasporti()));
        return spec;
    }

    private static Specification<Annunci> getAnnunciCaratteristiche(FiltriRicerca filtriRicerca, Specification<Annunci> spec) {
        if (filtriRicerca.getGarage() != null) spec = spec.and(AnnunciSpecification.hasGarage(filtriRicerca.getGarage()));
        if (filtriRicerca.getAscensore() != null) spec = spec.and(AnnunciSpecification.hasAscensore(filtriRicerca.getAscensore()));
        if (filtriRicerca.getPiscina() != null) spec = spec.and(AnnunciSpecification.hasPiscina(filtriRicerca.getPiscina()));
        if (filtriRicerca.getArredo() != null) spec = spec.and(AnnunciSpecification.hasArredo(filtriRicerca.getArredo()));
        if (filtriRicerca.getBalcone() != null) spec = spec.and(AnnunciSpecification.hasBalcone(filtriRicerca.getBalcone()));
        if (filtriRicerca.getGiardino() != null) spec = spec.and(AnnunciSpecification.hasGiardino(filtriRicerca.getGiardino()));
        return spec;
    }

    private static Specification<Annunci> getAnnunciNumeroStanzeMaxMin(FiltriRicerca filtriRicerca, Specification<Annunci> spec) {
        if (filtriRicerca.getNumStanzeMinime() != null) spec = spec.and(AnnunciSpecification.hasNumeroStanzeMinimo(filtriRicerca.getNumStanzeMinime()));
        if (filtriRicerca.getNumStanzeMassime() != null) spec = spec.and(AnnunciSpecification.hasNumeroStanzeMassimo(filtriRicerca.getNumStanzeMassime()));
        return spec;
    }

    private static Specification<Annunci> getAnnunciSuperficieMaxMin(FiltriRicerca filtriRicerca, Specification<Annunci> spec) {
        if (filtriRicerca.getSuperficieMinima() != null) spec = spec.and(AnnunciSpecification.hasSuperficieMinima(filtriRicerca.getSuperficieMinima()));
        if (filtriRicerca.getSuperficieMassima() != null) spec = spec.and(AnnunciSpecification.hasSuperficieMassima(filtriRicerca.getSuperficieMassima()));
        return spec;
    }

    private static Specification<Annunci> getAnnunciPrezzoMaxMin(FiltriRicerca filtriRicerca, Specification<Annunci> spec) {
        if (filtriRicerca.getPrezzoMinimo() != null) spec = spec.and(AnnunciSpecification.hasPrezzoMinimo(filtriRicerca.getPrezzoMinimo()));
        if (filtriRicerca.getPrezzoMassimo() != null) spec = spec.and(AnnunciSpecification.hasPrezzoMassimo(filtriRicerca.getPrezzoMassimo()));
        return spec;
    }

    public List<Annunci> getAnnunciByAgenteSub(String sub) {
        return annuncioRepository.findByAgenteSub(sub);
    }

    public List<Annunci> getAnnunciRecentiByClienteId(String id) {
        return annuncioRepository.findAnnunciRecentementeVisualizzatiByIdCliente(id);
    }

    public List<Annunci> getAnnunciConOffertePrenotazioniByAgenteSub(String sub, boolean offerte, boolean prenotazioni, boolean disponibili) {
        return annuncioRepository.findAnnunciConOffertePrenotazioniByAgenteSub(sub, offerte, prenotazioni, disponibili);
    }

    public void updateStatoAnnuncio(UUID idAnnuncio, StatoAnnuncio stato) {
        annuncioRepository.updateStatoAnnuncio(idAnnuncio, stato);
    }

    public Annunci getAnnuncioById(UUID idAnnuncio) {
        List<Annunci> annunci = annuncioRepository.getAnnuncioById(idAnnuncio);
        return annunci.get(0);
    }

}
