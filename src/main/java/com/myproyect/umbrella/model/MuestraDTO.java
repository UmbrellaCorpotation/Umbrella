package com.myproyect.umbrella.model;


import com.myproyect.umbrella.domain.GrupoMuestras;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class MuestraDTO {

    private Long id;
    private OffsetDateTime fechaObtencion;
    private GrupoMuestras grupoMuestras;
}
