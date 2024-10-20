package com.myproyect.umbrella.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class Muestra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private OffsetDateTime fechaObtencion;

    @ManyToOne
    @JoinColumn(name = "grupomuestras_id")
    private GrupoMuestras grupoMuestras;


}