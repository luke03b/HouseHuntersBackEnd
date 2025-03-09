package com.househuntersbackend.demo.classes.annuncio;

import com.househuntersbackend.demo.enumerations.ClasseEnergetica;
import com.househuntersbackend.demo.enumerations.Piano;

public class FiltriRicerca {
    private final String tipoAnnuncio;
    private final Coordinate coordinate;
    private final Double raggioKm;
    private final IntervalloPrezzo intervalloPrezzo;
    private final IntervalloSuperficie intervalloSuperficie;
    private final IntervalloStanze intervalloStanze;
    private final Caratteristiche caratteristiche;
    private final Vicinanze vicinanze;
    private final ClasseEnergetica classeEnergetica;
    private final Piano piano;

    public FiltriRicerca(String tipoAnnuncio, Coordinate coordinate, Double raggioKm, IntervalloPrezzo intervalloPrezzo,
                         IntervalloSuperficie intervalloSuperficie, IntervalloStanze intervalloStanze,
                         Caratteristiche caratteristiche, Vicinanze vicinanze, ClasseEnergetica classeEnergetica, Piano piano)
    {
        this.tipoAnnuncio = tipoAnnuncio;
        this.coordinate = coordinate;
        this.raggioKm = raggioKm;
        this.intervalloPrezzo = intervalloPrezzo;
        this.intervalloSuperficie = intervalloSuperficie;
        this.intervalloStanze = intervalloStanze;
        this.caratteristiche = caratteristiche;
        this.vicinanze = vicinanze;
        this.classeEnergetica = classeEnergetica;
        this.piano = piano;
    }

    public String getTipoAnnuncio() {
        return tipoAnnuncio;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Double getRaggioKm() {
        return raggioKm;
    }

    public IntervalloPrezzo getIntervalloPrezzo() {
        return intervalloPrezzo;
    }

    public IntervalloSuperficie getIntervalloSuperficie() {
        return intervalloSuperficie;
    }

    public IntervalloStanze getIntervalloStanze() {
        return intervalloStanze;
    }

    public Caratteristiche getCaratteristiche() {
        return caratteristiche;
    }

    public Vicinanze getVicinanze() {
        return vicinanze;
    }

    public ClasseEnergetica getClasseEnergetica() {
        return classeEnergetica;
    }

    public Piano getPiano() {
        return piano;
    }
}
