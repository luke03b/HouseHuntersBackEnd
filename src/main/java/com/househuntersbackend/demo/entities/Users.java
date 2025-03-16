package com.househuntersbackend.demo.entities;

import com.househuntersbackend.demo.enumerations.TipoUtente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false, name = "sub")
    private String sub;

    @Column(nullable = false, name = "email")
    private String email;

    @Column(nullable = false, name = "nome")
    private String nome;

    @Column(nullable = false, name = "cognome")
    private String cognome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "tipo")
    private TipoUtente tipo;

    @ManyToOne(optional = true)
    @JoinColumn(name = "idagenzia", nullable = true)
    private AgenziaImmobiliare agenzia;
}

