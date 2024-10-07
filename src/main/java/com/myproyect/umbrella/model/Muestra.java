package com.myproyect.umbrella.model;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Muestra")
@Data
public class Muestra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @Column(name = "fecha_recepcion")
    private LocalDateTime fechaRecepcion;

    @Column(name = "descripcion")
    private String descripcion;

    // Relaciones
    @OneToMany(mappedBy = "muestra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DatosGeneticos> datosGeneticos;

    @OneToMany(mappedBy = "muestra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DatosBioquimicos> datosBioquimicos;

    @OneToMany(mappedBy = "muestra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DatosFisicos> datosFisicos;

    @OneToMany(mappedBy = "muestra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evento> eventos;
}
