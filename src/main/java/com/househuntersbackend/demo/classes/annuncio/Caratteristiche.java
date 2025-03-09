package com.househuntersbackend.demo.classes.annuncio;

public class Caratteristiche {
    private final Boolean garage;
    private final Boolean ascensore;
    private final Boolean piscina;
    private final Boolean arredo;
    private final Boolean balcone;
    private final Boolean giardino;


    public Caratteristiche(Boolean garage, Boolean ascensore, Boolean piscina, Boolean arredo, Boolean balcone, Boolean giardino) {
        this.garage = garage;
        this.ascensore = ascensore;
        this.piscina = piscina;
        this.arredo = arredo;
        this.balcone = balcone;
        this.giardino = giardino;
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
}
