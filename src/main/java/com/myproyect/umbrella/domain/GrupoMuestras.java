package com.myproyect.umbrella.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "grupo_muestras")
@Getter
@Setter
public class GrupoMuestras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Muestra (uno a muchos)
    @OneToMany(mappedBy = "grupoMuestras", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Muestra> muestras = new ArrayList<>();

    // Relación con Grafico (uno a muchos)
    @OneToMany(mappedBy = "grupoMuestras", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Grafico> graficos = new ArrayList<>();

    public void addMuestra(Muestra muestra) {
        if (this.muestras.size() < 50) { // Limitación de 50 muestras por grupo
            this.muestras.add(muestra);
            muestra.setGrupoMuestras(this);
        } else {
            throw new RuntimeException("El grupo ya tiene 50 muestras");
        }
    }

    public void removeMuestra(Muestra muestra) {
        this.muestras.remove(muestra);
        muestra.setGrupoMuestras(null);
    }
}
