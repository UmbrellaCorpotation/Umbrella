package com.myproyect.umbrella.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "DatoBioquimicoD")
public class DatoBioquimico extends Muestra {

    private String compuestoQuimico;
    private Double concentracion;

    // Getters y setters
}
