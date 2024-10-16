package com.myproyect.umbrella.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MuestraDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date fechaObtencion;
    private String origen;
    private String descripcion;
    private String tipo; // Puede ser "genetica", "bioquimica", "fisica"

}
