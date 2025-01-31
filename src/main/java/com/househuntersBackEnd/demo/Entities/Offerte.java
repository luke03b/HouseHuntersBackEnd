package com.househuntersBackEnd.demo.Entities;

import com.househuntersBackEnd.demo.Enumerations.StatoOfferta;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Offerte {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "idAnnuncio")
    private Annunci annuncio;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Users cliente;

    private double prezzo;
    private java.time.LocalDate data;

    @Enumerated(EnumType.STRING)
    private StatoOfferta stato;

    private Double controProposta;
}

