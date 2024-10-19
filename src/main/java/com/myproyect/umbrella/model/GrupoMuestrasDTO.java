package com.myproyect.umbrella.model;

import com.myproyect.umbrella.domain.Muestra;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GrupoMuestrasDTO {

    private Long id;
    private List<Muestra> muestras;

}