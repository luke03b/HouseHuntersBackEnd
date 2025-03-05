package com.househuntersBackEnd.demo;

import com.househuntersBackEnd.demo.Entities.Annunci;
import com.househuntersBackEnd.demo.Entities.Offerte;
import com.househuntersBackEnd.demo.Enumerations.ClasseEnergetica;
import com.househuntersBackEnd.demo.Enumerations.Piano;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

public class AnnunciSpecification {

    public static Specification<Annunci> hasTipoAnnuncio(String tipo_annuncio) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("tipo_annuncio"), tipo_annuncio);
    }

    public static Specification<Annunci> hasStatoAnnuncio(String stato) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("stato"), stato);
    }

    public static Specification<Annunci> hasPrezzoMinimo(double prezzoMinimo) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("prezzo"), prezzoMinimo));
    }

    public static Specification<Annunci> hasPrezzoMassimo(double prezzoMassimo) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("prezzo"), prezzoMassimo));
    }

    public static Specification<Annunci> hasSuperficieMinima(int superficieMinima) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("superficie"), superficieMinima);
    }

    public static Specification<Annunci> hasSuperficieMassima(int superficieMassima) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("superficie"), superficieMassima);
    }

    public static Specification<Annunci> hasNumeroStanzeMinimo(int numStanzeMinime) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("numStanze"), numStanzeMinime);
    }

    public static Specification<Annunci> hasNumeroStanzeMassimo(int numStanzeMassime) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("numStanze"), numStanzeMassime);
    }

    public static Specification<Annunci> hasGarage(boolean garage) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("garage"), garage);
    }

    public static Specification<Annunci> hasAscensore(boolean ascensore) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ascensore"), ascensore);
    }

    public static Specification<Annunci> hasPiscina(boolean piscina) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("piscina"), piscina);
    }

    public static Specification<Annunci> hasArredo(boolean arredo) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("arredo"), arredo);
    }

    public static Specification<Annunci> hasBalcone(boolean balcone) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("balcone"), balcone);
    }

    public static Specification<Annunci> hasGiardino(boolean giardino) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("giardino"), giardino);
    }

    public static Specification<Annunci> hasVicinoScuole(boolean vicino_scuole) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("vicino_scuole"), vicino_scuole);
    }

    public static Specification<Annunci> hasVicinoParchi(boolean vicino_parchi) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("vicino_parchi"), vicino_parchi);
    }

    public static Specification<Annunci> hasVicinoTrasporti(boolean vicino_trasporti) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("vicino_trasporti"), vicino_trasporti);
    }

    public static Specification<Annunci> hasClasseEnergetica(ClasseEnergetica classeEnergetica) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("classe_energetica"), classeEnergetica);
    }

    public static Specification<Annunci> hasPiano(Piano piano) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("piano"), piano);
    }

    public static Specification<Annunci> hasIndirizzo(String indirizzo) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("indirizzo"), "%" + indirizzo + "%");
    }

    public static Specification<Annunci> hasLatitudine(double latitudine) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("latitudine"), latitudine);
    }

    public static Specification<Annunci> hasLongitudine(double longitudine) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("longitudine"), longitudine);
    }

    public static Specification<Annunci> hasLatitudineBetween(double minLat, double maxLat) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("latitudine"), minLat, maxLat);
    }

    public static Specification<Annunci> hasLongitudineBetween(double minLon, double maxLon) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("longitudine"), minLon, maxLon);
    }

}
