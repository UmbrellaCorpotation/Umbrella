package com.myproyect.umbrella.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatoBioquimicoDTO {

    private Long id;
    private String compuestoQuimico;
    private Double concentracion;
}
