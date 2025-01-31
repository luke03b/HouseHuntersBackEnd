package com.househuntersBackEnd.demo.Entities;

import com.househuntersBackEnd.demo.Enumerations.StatoVisita;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visite {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "idAnnuncio")
    private Annunci annuncio;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Users cliente;

    private java.time.LocalDate data;
    private java.time.LocalTime orarioInizio;
    private java.time.LocalTime orarioFine;

    @Enumerated(EnumType.STRING)
    private StatoVisita stato;
}

