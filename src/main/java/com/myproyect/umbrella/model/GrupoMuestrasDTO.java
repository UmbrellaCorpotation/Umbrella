package com.myproyect.umbrella.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GrupoMuestrasDTO {

    private Long id;

    private String nombre;

    private List<Long> muestrasIds;

    private List<Long> graficosIds;
}
