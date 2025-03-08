package com.househuntersbackend.demo.entities;

import com.househuntersbackend.demo.enumerations.StatoVisita;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visite {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "idAnnuncio")
    private Annunci annuncio;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Users cliente;

    @Column(nullable = false, name = "data")
    private java.time.LocalDate data;

    @Column(nullable = false, name = "orarioInizio")
    private java.time.LocalTime orarioInizio;

    @Column(nullable = false, name = "orarioFine")
    private java.time.LocalTime orarioFine;

    @Enumerated(EnumType.STRING)
    private StatoVisita stato;
}

