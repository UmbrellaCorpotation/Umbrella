package com.myproyect.umbrella.model;


import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "Datos_Bioquímicos")
@Data
public class DatosBioquimicos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sample_id", nullable = false)
    private Muestra muestra;

    @Column(name = "compuestos_detectados")
    private String compuestosDetectados;

    @Column(name = "concentraciones")
    private BigDecimal concentraciones;
}
