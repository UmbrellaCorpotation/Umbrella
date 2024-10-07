package com.myproyect.umbrella.model;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "Eventos")
@Data
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tipo_evento", nullable = false)
    private String tipoEvento;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "sample_id", nullable = false)
    private Muestra muestra;
}
