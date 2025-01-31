package com.househuntersBackEnd.demo.Entities;

import com.househuntersBackEnd.demo.Enumerations.ClasseEnergetica;
import com.househuntersBackEnd.demo.Enumerations.Piano;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Annunci {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private double prezzo;
    private int superficie;
    private int numStanze;
    private boolean garage;
    private boolean ascensore;
    private boolean piscina;
    private boolean arredo;
    private boolean balcone;
    private boolean giardino;
    private boolean vicinoScuole;
    private boolean vicinoParchi;
    private boolean vicinoTrasporti;

    @Enumerated(EnumType.STRING)
    private ClasseEnergetica classeEnergetica;

    @Enumerated(EnumType.STRING)
    private Piano piano;

    private java.time.LocalDate dataCreazione;

    @ManyToOne
    @JoinColumn(name = "idAgente")
    private Users agente;

    private String indirizzo;
    private double coordinatex;
    private double coordinatey;
}

