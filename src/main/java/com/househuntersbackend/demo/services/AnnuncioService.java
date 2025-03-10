package com.househuntersbackend.demo.services;

import com.househuntersbackend.demo.AnnunciSpecification;
import com.househuntersbackend.demo.entities.Annunci;
import com.househuntersbackend.demo.classes.annuncio.FiltriRicerca;
import com.househuntersbackend.demo.enumerations.StatoAnnuncio;
import com.househuntersbackend.demo.exceptions.AnnuncioNonEsistenteException;
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

        if(filtriRicerca.getIntervalloPrezzo() != null) {
            spec = getAnnunciPrezzoMaxMin(filtriRicerca, spec);
        }

        if(filtriRicerca.getIntervalloSuperficie() != null) {
            spec = getAnnunciSuperficieMaxMin(filtriRicerca, spec);
        }

        if(filtriRicerca.getIntervalloStanze() != null) {
            spec = getAnnunciNumeroStanzeMaxMin(filtriRicerca, spec);
        }

        if(filtriRicerca.getCaratteristiche() != null) {
            spec = getAnnunciCaratteristiche(filtriRicerca, spec);
        }

        if(filtriRicerca.getVicinanze() != null) {
            spec = getAnnunciVicinanze(filtriRicerca, spec);
        }

        spec = getAnnunciOthers(filtriRicerca, spec);
        spec = spec.and(AnnunciSpecification.hasStatoAnnuncio(StatoAnnuncio.DISPONIBILE.toString()));



        if (filtriRicerca.getCoordinate().getLatitudine() != null && filtriRicerca.getCoordinate().getLongitudine() != null && filtriRicerca.getRaggioKm() != null) {
            double[] deltas = GeoUtils.kmToDegrees(filtriRicerca.getCoordinate().getLatitudine(), filtriRicerca.getRaggioKm());
            double minLat = filtriRicerca.getCoordinate().getLatitudine() - deltas[0];
            double maxLat = filtriRicerca.getCoordinate().getLatitudine() + deltas[0];
            double minLon = filtriRicerca.getCoordinate().getLongitudine() - deltas[1];
            double maxLon = filtriRicerca.getCoordinate().getLongitudine() + deltas[1];

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
        if (filtriRicerca.getVicinanze().getVicinoScuole() != null) spec = spec.and(AnnunciSpecification.hasVicinoScuole(filtriRicerca.getVicinanze().getVicinoScuole()));
        if (filtriRicerca.getVicinanze().getVicinoParchi() != null) spec = spec.and(AnnunciSpecification.hasVicinoParchi(filtriRicerca.getVicinanze().getVicinoParchi()));
        if (filtriRicerca.getVicinanze().getVicinoTrasporti() != null) spec = spec.and(AnnunciSpecification.hasVicinoTrasporti(filtriRicerca.getVicinanze().getVicinoTrasporti()));
        return spec;
    }

    private static Specification<Annunci> getAnnunciCaratteristiche(FiltriRicerca filtriRicerca, Specification<Annunci> spec) {
        if (filtriRicerca.getCaratteristiche().getGarage() != null) spec = spec.and(AnnunciSpecification.hasGarage(filtriRicerca.getCaratteristiche().getGarage()));
        if (filtriRicerca.getCaratteristiche().getAscensore() != null) spec = spec.and(AnnunciSpecification.hasAscensore(filtriRicerca.getCaratteristiche().getAscensore()));
        if (filtriRicerca.getCaratteristiche().getPiscina() != null) spec = spec.and(AnnunciSpecification.hasPiscina(filtriRicerca.getCaratteristiche().getPiscina()));
        if (filtriRicerca.getCaratteristiche().getArredo() != null) spec = spec.and(AnnunciSpecification.hasArredo(filtriRicerca.getCaratteristiche().getArredo()));
        if (filtriRicerca.getCaratteristiche().getBalcone() != null) spec = spec.and(AnnunciSpecification.hasBalcone(filtriRicerca.getCaratteristiche().getBalcone()));
        if (filtriRicerca.getCaratteristiche().getGiardino() != null) spec = spec.and(AnnunciSpecification.hasGiardino(filtriRicerca.getCaratteristiche().getGiardino()));
        return spec;
    }

    private static Specification<Annunci> getAnnunciNumeroStanzeMaxMin(FiltriRicerca filtriRicerca, Specification<Annunci> spec) {
        if (filtriRicerca.getIntervalloStanze().getNumStanzeMinime() != null) spec = spec.and(AnnunciSpecification.hasNumeroStanzeMinimo(filtriRicerca.getIntervalloStanze().getNumStanzeMinime()));
        if (filtriRicerca.getIntervalloStanze().getNumStanzeMassime() != null) spec = spec.and(AnnunciSpecification.hasNumeroStanzeMassimo(filtriRicerca.getIntervalloStanze().getNumStanzeMassime()));
        return spec;
    }

    private static Specification<Annunci> getAnnunciSuperficieMaxMin(FiltriRicerca filtriRicerca, Specification<Annunci> spec) {
        if (filtriRicerca.getIntervalloSuperficie().getSuperficieMinima() != null) spec = spec.and(AnnunciSpecification.hasSuperficieMinima(filtriRicerca.getIntervalloSuperficie().getSuperficieMinima()));
        if (filtriRicerca.getIntervalloSuperficie().getSuperficieMassima() != null) spec = spec.and(AnnunciSpecification.hasSuperficieMassima(filtriRicerca.getIntervalloSuperficie().getSuperficieMassima()));
        return spec;
    }

    private static Specification<Annunci> getAnnunciPrezzoMaxMin(FiltriRicerca filtriRicerca, Specification<Annunci> spec) {
        if (filtriRicerca.getIntervalloPrezzo().getPrezzoMinimo() != null) spec = spec.and(AnnunciSpecification.hasPrezzoMinimo(filtriRicerca.getIntervalloPrezzo().getPrezzoMinimo()));
        if (filtriRicerca.getIntervalloPrezzo().getPrezzoMassimo() != null) spec = spec.and(AnnunciSpecification.hasPrezzoMassimo(filtriRicerca.getIntervalloPrezzo().getPrezzoMassimo()));
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
        return annuncioRepository.findById(idAnnuncio)
                .orElseThrow(() -> new AnnuncioNonEsistenteException("Annuncio con ID " + idAnnuncio + " non trovato"));
    }

}
