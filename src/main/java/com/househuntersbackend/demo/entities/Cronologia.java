package com.househuntersbackend.demo.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cronologia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Users cliente;

    @ManyToOne
    @JoinColumn(name = "idAnnuncio")
    private Annunci annuncio;

    @Column(nullable = false, name = "dataVisualizzazione")
    private java.time.LocalDateTime dataVisualizzazione;
}
