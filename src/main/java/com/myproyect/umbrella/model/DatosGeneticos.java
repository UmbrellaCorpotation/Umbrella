package com.myproyect.umbrella.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Datos_Genéticos")
@Data
public class DatosGeneticos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sample_id", nullable = false)
    private Muestra muestra;

    @Column(name = "secuencia")
    private String secuencia;

    @Column(name = "genes_identificados")
    private String genesIdentificados;
}
