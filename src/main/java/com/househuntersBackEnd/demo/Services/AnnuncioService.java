package com.househuntersBackEnd.demo.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.househuntersBackEnd.demo.AnnunciSpecification;
import com.househuntersBackEnd.demo.Entities.Annunci;
import com.househuntersBackEnd.demo.Entities.FiltriRicerca;
import com.househuntersBackEnd.demo.Enumerations.ClasseEnergetica;
import com.househuntersBackEnd.demo.Enumerations.Piano;
import com.househuntersBackEnd.demo.Repositories.AnnuncioRepository;
import com.househuntersBackEnd.demo.Utils.AnnuncioUtils;
import com.househuntersBackEnd.demo.Utils.GeoUtils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnnuncioService {

    @Autowired
    private AnnuncioRepository annuncioRepository;
    private final AnnuncioUtils annuncioUtils = new AnnuncioUtils();


    public Annunci createAnnuncio(Annunci annuncio) {
        annuncio.setData_creazione(LocalDateTime.now());

        annuncioUtils.setVicinanze(annuncio);

        return annuncioRepository.save(annuncio);
    }


    public List<Annunci> getAnnunci(FiltriRicerca filtriRicerca) {
        Specification<Annunci> spec = Specification.where(null);

        if (filtriRicerca.getPrezzoMinimo() != null) spec = spec.and(AnnunciSpecification.hasPrezzoMinimo(filtriRicerca.getPrezzoMinimo()));
        if (filtriRicerca.getPrezzoMassimo() != null) spec = spec.and(AnnunciSpecification.hasPrezzoMassimo(filtriRicerca.getPrezzoMassimo()));
        if (filtriRicerca.getSuperficieMinima() != null) spec = spec.and(AnnunciSpecification.hasSuperficieMinima(filtriRicerca.getSuperficieMinima()));
        if (filtriRicerca.getSuperficieMassima() != null) spec = spec.and(AnnunciSpecification.hasSuperficieMassima(filtriRicerca.getSuperficieMassima()));
        if (filtriRicerca.getNumStanzeMinime() != null) spec = spec.and(AnnunciSpecification.hasNumeroStanzeMinimo(filtriRicerca.getNumStanzeMinime()));
        if (filtriRicerca.getNumStanzeMassime() != null) spec = spec.and(AnnunciSpecification.hasNumeroStanzeMassimo(filtriRicerca.getNumStanzeMassime()));
        if (filtriRicerca.getGarage() != null) spec = spec.and(AnnunciSpecification.hasGarage(filtriRicerca.getGarage()));
        if (filtriRicerca.getAscensore() != null) spec = spec.and(AnnunciSpecification.hasAscensore(filtriRicerca.getAscensore()));
        if (filtriRicerca.getPiscina() != null) spec = spec.and(AnnunciSpecification.hasPiscina(filtriRicerca.getPiscina()));
        if (filtriRicerca.getArredo() != null) spec = spec.and(AnnunciSpecification.hasArredo(filtriRicerca.getArredo()));
        if (filtriRicerca.getBalcone() != null) spec = spec.and(AnnunciSpecification.hasBalcone(filtriRicerca.getBalcone()));
        if (filtriRicerca.getGiardino() != null) spec = spec.and(AnnunciSpecification.hasGiardino(filtriRicerca.getGiardino()));
        if (filtriRicerca.getVicino_scuole() != null) spec = spec.and(AnnunciSpecification.hasVicinoScuole(filtriRicerca.getVicino_scuole()));
        if (filtriRicerca.getVicino_parchi() != null) spec = spec.and(AnnunciSpecification.hasVicinoParchi(filtriRicerca.getVicino_parchi()));
        if (filtriRicerca.getVicino_trasporti() != null) spec = spec.and(AnnunciSpecification.hasVicinoTrasporti(filtriRicerca.getVicino_trasporti()));
        if (filtriRicerca.getClasseEnergetica() != null) spec = spec.and(AnnunciSpecification.hasClasseEnergetica(filtriRicerca.getClasseEnergetica()));
        if (filtriRicerca.getPiano() != null) spec = spec.and(AnnunciSpecification.hasPiano(filtriRicerca.getPiano()));
        if (filtriRicerca.getTipo_annuncio() != null) spec = spec.and(AnnunciSpecification.hasTipoAnnuncio(filtriRicerca.getTipo_annuncio()));



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

    public List<Annunci> getAnnunciByAgenteSub(String sub) {
        return annuncioRepository.findByAgenteSub(sub);
    }

    public List<Annunci> getAnnunciByClienteId(String id) {
        return annuncioRepository.findUltimiTreAnnunciByIdCliente(id);
    }

}
