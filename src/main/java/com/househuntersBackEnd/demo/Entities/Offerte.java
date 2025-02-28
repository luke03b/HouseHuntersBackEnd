package com.househuntersBackEnd.demo.Entities;

import com.househuntersBackEnd.demo.Enumerations.StatoOfferta;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Offerte {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "idAnnuncio")
    private Annunci annuncio;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Users cliente;

    @Column(nullable = false, name = "prezzo")
    private double prezzo;

    @Column(nullable = false, name = "data")
    private java.time.LocalDateTime data;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "stato")
    private StatoOfferta stato;

    @Column(nullable = true, name = "controProposta")
    private Double controProposta;
}

