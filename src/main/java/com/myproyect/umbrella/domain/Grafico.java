package com.myproyect.umbrella.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Entity
@Table(name = "graficos")
@Getter
@Setter
public class Grafico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con GrupoMuestras (muchos a uno)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_muestras_id", nullable = false)
    private GrupoMuestras grupoMuestras;

    // Datos procesados para el gráfico (clave-valor)
    @ElementCollection
    @CollectionTable(name = "grafico_datos", joinColumns = @JoinColumn(name = "grafico_id"))
    @MapKeyColumn(name = "clave")
    @Column(name = "valor")
    private Map<String, Double> datosProcesados;

    // Marca de tiempo
    @Column(nullable = false)
    private Long timestamp;
}


