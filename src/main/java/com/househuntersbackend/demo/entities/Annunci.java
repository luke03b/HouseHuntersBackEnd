package com.househuntersbackend.demo.entities;

import com.househuntersbackend.demo.enumerations.ClasseEnergetica;
import com.househuntersbackend.demo.enumerations.Piano;
import com.househuntersbackend.demo.enumerations.StatoAnnuncio;
import com.househuntersbackend.demo.enumerations.TipoAnnuncio;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Annunci {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, name = "tipo_annuncio")
    @Enumerated(EnumType.STRING)
    private TipoAnnuncio tipoAnnuncio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "stato")
    private StatoAnnuncio stato;

    @Column(nullable = false, name = "prezzo")
    private double prezzo;

    @Column(nullable = false, name = "superficie")
    private int superficie;

    @Column(nullable = false, name = "num_stanze")
    private int numStanze;

    @Column(nullable = false, name = "garage")
    private boolean garage;

    @Column(nullable = false, name = "ascensore")
    private boolean ascensore;

    @Column(nullable = false, name = "piscina")
    private boolean piscina;

    @Column(nullable = false, name = "arredo")
    private boolean arredo;

    @Column(nullable = false, name = "balcone")
    private boolean balcone;

    @Column(nullable = false, name = "giardino")
    private boolean giardino;

    @Column(nullable = false, name = "vicino_scuole")
    private boolean vicinoScuole;

    @Column(nullable = false, name = "vicino_parchi")
    private boolean vicinoParchi;

    @Column(nullable = false, name = "vicino_trasporti")
    private boolean vicinoTrasporti;

    @Column(nullable = false, name = "classe_energetica")
    @Enumerated(EnumType.STRING)
    private ClasseEnergetica classeEnergetica;

    @Column(nullable = false, name = "piano")
    @Enumerated(EnumType.STRING)
    private Piano piano;

    @Column(name = "numeropiano")
    private Integer numeroPiano;

    @Column(nullable = false, name = "data_creazione")
    private java.time.LocalDateTime dataCreazione;

    @ManyToOne
    @JoinColumn(name = "idAgente")
    private Users agente;

    @Column(nullable = false, name = "indirizzo")
    private String indirizzo;

    @Column(nullable = false, name = "latitudine")
    private double latitudine;

    @Column(nullable = false, name = "longitudine")
    private double longitudine;

    @Column(nullable = false, name = "descrizione", columnDefinition = "TEXT")
    private String descrizione;
}

