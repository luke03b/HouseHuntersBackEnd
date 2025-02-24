package com.househuntersBackEnd.demo.Entities;

import com.househuntersBackEnd.demo.Enumerations.UserType;
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

    //Enum per CLIENTE, AGENTE, ADMIN
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "tipo")
    private UserType tipo;

    //FK all'agenzia
    @ManyToOne(optional = true)
    @JoinColumn(name = "idagenzia", nullable = true)
    private AgenziaImmobiliare agenzia;
}

