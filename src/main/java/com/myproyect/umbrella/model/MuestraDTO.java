package com.myproyect.umbrella.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MuestraDTO {

    private Long id;
    private Date fechaObtencion;
    private String origen;
    private String descripcion;
    private String tipo; // Puede ser "genetica", "bioquimica", "fisica"

}
