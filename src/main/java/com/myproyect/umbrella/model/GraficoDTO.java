package com.myproyect.umbrella.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class GraficoDTO {

    private Long id;

    private Long grupoMuestrasId;

    private Map<String, Double> datosProcesados;

    private Long timestamp;
}
