package com.myproyect.umbrella.model;


import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "Datos_Físicos")
@Data
public class DatosFisicos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sample_id", nullable = false)
    private Muestra muestra;

    @Column(name = "peso")
    private BigDecimal peso;

    @Column(name = "altura")
    private BigDecimal altura;

    @Column(name = "temperatura")
    private BigDecimal temperatura;
}

