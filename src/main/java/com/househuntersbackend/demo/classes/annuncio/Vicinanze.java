package com.househuntersbackend.demo.classes.annuncio;

public class Vicinanze {
    private final Boolean vicinoScuole;
    private final Boolean vicinoParchi;
    private final Boolean vicinoTrasporti;


    public Vicinanze(Boolean vicinoScuole, Boolean vicinoParchi, Boolean vicinoTrasporti) {
        this.vicinoScuole = vicinoScuole;
        this.vicinoParchi = vicinoParchi;
        this.vicinoTrasporti = vicinoTrasporti;
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
}
