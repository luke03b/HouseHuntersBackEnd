package com.househuntersbackend.demo.classes.annuncio;

public class IntervalloPrezzo {
    private final Double prezzoMinimo;
    private final Double prezzoMassimo;


    public IntervalloPrezzo(Double prezzoMinimo, Double prezzoMassimo) {
        this.prezzoMinimo = prezzoMinimo;
        this.prezzoMassimo = prezzoMassimo;
    }

    public Double getPrezzoMinimo() {
        return prezzoMinimo;
    }

    public Double getPrezzoMassimo() {
        return prezzoMassimo;
    }
}
