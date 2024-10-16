package com.myproyect.umbrella.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table (name="DatoFisicoD")
public class DatoFisico extends Muestra {

    private String medida;
    private Double valor;
    private String unidad;

    // Getters y setters
}
