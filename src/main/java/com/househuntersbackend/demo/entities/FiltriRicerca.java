package com.househuntersbackend.demo.entities;

import com.househuntersbackend.demo.enumerations.ClasseEnergetica;
import com.househuntersbackend.demo.enumerations.Piano;

public class FiltriRicerca {
    private String tipo_annuncio;
    private Double latitudine;
    private Double longitudine;
    private Double raggioKm;
    private Double prezzoMinimo;
    private Double prezzoMassimo;
    private Integer superficieMinima;
    private Integer superficieMassima;
    private Integer numStanzeMinime;
    private Integer numStanzeMassime;
    private Boolean garage;
    private Boolean ascensore;
    private Boolean piscina;
    private Boolean arredo;
    private Boolean balcone;
    private Boolean giardino;
    private Boolean vicino_scuole;
    private Boolean vicino_parchi;
    private Boolean vicino_trasporti;
    private ClasseEnergetica classeEnergetica;
    private Piano piano;

    public FiltriRicerca(
        String tipo_annuncio,
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
        Boolean vicino_scuole,
        Boolean vicino_parchi,
        Boolean vicino_trasporti,
        ClasseEnergetica classeEnergetica,
        Piano piano
    ) {
        this.tipo_annuncio = tipo_annuncio;
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
        this.vicino_scuole = vicino_scuole;
        this.vicino_parchi = vicino_parchi;
        this.vicino_trasporti = vicino_trasporti;
        this.piano = piano;
        this.classeEnergetica = classeEnergetica;
    }

    public String getTipo_annuncio() {
        return tipo_annuncio;
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

    public Boolean getVicino_scuole() {
        return vicino_scuole;
    }

    public Boolean getVicino_parchi() {
        return vicino_parchi;
    }

    public Boolean getVicino_trasporti() {
        return vicino_trasporti;
    }

    public ClasseEnergetica getClasseEnergetica() {
        return classeEnergetica;
    }

    public Piano getPiano() {
        return piano;
    }
}
