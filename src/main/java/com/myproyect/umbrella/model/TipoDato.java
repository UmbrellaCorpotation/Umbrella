package com.myproyect.umbrella.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Tipo_Dato")
@Data
public class TipoDato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;
}
