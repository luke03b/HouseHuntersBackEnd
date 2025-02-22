package com.househuntersBackEnd.demo.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.househuntersBackEnd.demo.AnnunciSpecification;
import com.househuntersBackEnd.demo.Entities.Annunci;
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


    public List<Annunci> getAnnunci(
            Double prezzoMinimo,
            Double prezzoMassimo,
            Integer superficieMinima,
            Integer superficieMassima,
            Integer numStanzeMinime,
            Integer numStanzeMassime,
            Boolean garage,
            Boolean ascensore,
            Boolean piscina,
            Boolean arredo,
            Boolean balcone,
            Boolean giardino,
            Boolean vicino_scuole,
            Boolean vicino_parchi,
            Boolean vicino_trasporti,
            ClasseEnergetica classeEnergetica,
            Piano piano,
            String indirizzo,
            Double latitudine,
            Double longitudine,
            Double raggioKm
    ) {
        Specification<Annunci> spec = Specification.where(null);

        if (prezzoMinimo != null) spec = spec.and(AnnunciSpecification.hasPrezzoMinimo(prezzoMinimo));
        if (prezzoMassimo != null) spec = spec.and(AnnunciSpecification.hasPrezzoMassimo(prezzoMassimo));
        if (superficieMinima != null) spec = spec.and(AnnunciSpecification.hasSuperficieMinima(superficieMinima));
        if (superficieMassima != null) spec = spec.and(AnnunciSpecification.hasSuperficieMassima(superficieMassima));
        if (numStanzeMinime != null) spec = spec.and(AnnunciSpecification.hasNumeroStanzeMinimo(numStanzeMinime));
        if (numStanzeMassime != null) spec = spec.and(AnnunciSpecification.hasNumeroStanzeMassimo(numStanzeMassime));
        if (garage != null) spec = spec.and(AnnunciSpecification.hasGarage(garage));
        if (ascensore != null) spec = spec.and(AnnunciSpecification.hasAscensore(ascensore));
        if (piscina != null) spec = spec.and(AnnunciSpecification.hasPiscina(piscina));
        if (arredo != null) spec = spec.and(AnnunciSpecification.hasArredo(arredo));
        if (balcone != null) spec = spec.and(AnnunciSpecification.hasBalcone(balcone));
        if (giardino != null) spec = spec.and(AnnunciSpecification.hasGiardino(giardino));
        if (vicino_scuole != null) spec = spec.and(AnnunciSpecification.hasVicinoScuole(vicino_scuole));
        if (vicino_parchi != null) spec = spec.and(AnnunciSpecification.hasVicinoParchi(vicino_parchi));
        if (vicino_trasporti != null) spec = spec.and(AnnunciSpecification.hasVicinoTrasporti(vicino_trasporti));
        if (classeEnergetica != null) spec = spec.and(AnnunciSpecification.hasClasseEnergetica(classeEnergetica));
        if (piano != null) spec = spec.and(AnnunciSpecification.hasPiano(piano));
        if (indirizzo != null) spec = spec.and(AnnunciSpecification.hasIndirizzo(indirizzo));

        if (latitudine != null && longitudine != null && raggioKm != null) {
            double[] deltas = GeoUtils.kmToDegrees(latitudine, raggioKm);
            double minLat = latitudine - deltas[0];
            double maxLat = latitudine + deltas[0];
            double minLon = longitudine - deltas[1];
            double maxLon = longitudine + deltas[1];

            spec = spec.and(AnnunciSpecification.hasLatitudineBetween(minLat, maxLat))
                    .and(AnnunciSpecification.hasLongitudineBetween(minLon, maxLon));
        }

        return annuncioRepository.findAll(spec);
    }
}
