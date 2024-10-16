package com.myproyect.umbrella.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table (name =  "DatoGeneticoD")
public class DatoGenetico extends Muestra {

    private String secuenciaADN;
    private String observaciones;

    // Getters y setters
}
