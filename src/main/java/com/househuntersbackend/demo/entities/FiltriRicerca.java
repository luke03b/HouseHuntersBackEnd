package com.househuntersbackend.demo.entities;

import com.househuntersbackend.demo.enumerations.ClasseEnergetica;
import com.househuntersbackend.demo.enumerations.Piano;

public class FiltriRicerca {
    private final String tipoAnnuncio;
    private final Double latitudine;
    private final Double longitudine;
    private final Double raggioKm;
    private final Double prezzoMinimo;
    private final Double prezzoMassimo;
    private final Integer superficieMinima;
    private final Integer superficieMassima;
    private final Integer numStanzeMinime;
    private final Integer numStanzeMassime;
    private final Boolean garage;
    private final Boolean ascensore;
    private final Boolean piscina;
    private final Boolean arredo;
    private final Boolean balcone;
    private final Boolean giardino;
    private final Boolean vicinoScuole;
    private final Boolean vicinoParchi;
    private final Boolean vicinoTrasporti;
    private final ClasseEnergetica classeEnergetica;
    private final Piano piano;

    public FiltriRicerca(
        String tipoAnnuncio,
        Double latitudine,
        Double longitudine,
        Double raggioKm,
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
        Boolean vicinoScuole,
        Boolean vicinoParchi,
        Boolean vicinoTrasporti,
        ClasseEnergetica classeEnergetica,
        Piano piano
    ) {
        this.tipoAnnuncio = tipoAnnuncio;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.raggioKm = raggioKm;
        this.prezzoMinimo = prezzoMinimo;
        this.prezzoMassimo = prezzoMassimo;
        this.superficieMinima = superficieMinima;
        this.superficieMassima = superficieMassima;
        this.numStanzeMinime = numStanzeMinime;
        this.numStanzeMassime = numStanzeMassime;
        this.garage = garage;
        this.ascensore  = ascensore;
        this.arredo = arredo;
        this.giardino = giardino;
        this.piscina = piscina;
        this.balcone = balcone;
        this.vicinoScuole = vicinoScuole;
        this.vicinoParchi = vicinoParchi;
        this.vicinoTrasporti = vicinoTrasporti;
        this.piano = piano;
        this.classeEnergetica = classeEnergetica;
    }

    public String getTipoAnnuncio() {
        return tipoAnnuncio;
    }

    public Double getLatitudine() {
        return latitudine;
    }

    public Double getLongitudine() {
        return longitudine;
    }

    public Double getRaggioKm() {
        return raggioKm;
    }

    public Double getPrezzoMinimo() {
        return prezzoMinimo;
    }

    public Double getPrezzoMassimo() {
        return prezzoMassimo;
    }

    public Integer getSuperficieMinima() {
        return superficieMinima;
    }

    public Integer getSuperficieMassima() {
        return superficieMassima;
    }

    public Integer getNumStanzeMinime() {
        return numStanzeMinime;
    }

    public Integer getNumStanzeMassime() {
        return numStanzeMassime;
    }

    public Boolean getGarage() {
        return garage;
    }

    public Boolean getAscensore() {
        return ascensore;
    }

    public Boolean getPiscina() {
        return piscina;
    }

    public Boolean getArredo() {
        return arredo;
    }

    public Boolean getBalcone() {
        return balcone;
    }

    public Boolean getGiardino() {
        return giardino;
    }

    public Boolean getVicinoScuole() {
        return vicinoScuole;
    }

    public Boolean getVicinoParchi() {
        return vicinoParchi;
    }

    public Boolean getVicinoTrasporti() {
        return vicinoTrasporti;
    }

    public ClasseEnergetica getClasseEnergetica() {
        return classeEnergetica;
    }

    public Piano getPiano() {
        return piano;
    }
}
