package com.myproyect.umbrella.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Tipo_Dato")
@Data
public class TipoDato {

    @Id
    private Integer id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;
}
