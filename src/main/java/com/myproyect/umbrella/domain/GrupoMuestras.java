package com.myproyect.umbrella.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@EntityListeners(GrupoMuestras.class)
@Getter
@Setter
@Table(name="GrupoMuestrasD")
public class GrupoMuestras {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "GrupoMUestras", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Muestra> muestras;
}
