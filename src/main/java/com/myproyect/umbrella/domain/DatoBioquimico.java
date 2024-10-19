package com.myproyect.umbrella.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@EntityListeners(DatoBioquimico.class)
@Getter
@Setter
public class DatoBioquimico extends Muestra {

    @Id
    private Long id;
    private String name;
    private String category;
    private String dosageForm;
    private String strength;
    private String manufacturer;
    private String indication;
    private String classification;



    // Getters y Setters
}
