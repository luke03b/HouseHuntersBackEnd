package com.househuntersBackEnd.demo.Entities;

import com.househuntersBackEnd.demo.Enumerations.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    //Id univoco legato a Cognito
    @Id
    private String sub;

    //Enum per CLIENTE, AGENTE, ADMIN
    @Enumerated(EnumType.STRING)
    private UserType tipo;

    //FK all'agenzia
    @ManyToOne
    @JoinColumn(name = "idAgenzia")
    private AgenzieImmobiliari agenzia;
}

