package com.myproyect.umbrella.service;

import com.myproyect.umbrella.domain.*;
import org.springframework.stereotype.Service;

@Service
public class MuestraFactoryService {

    public Muestra crearMuestra(String tipo) {
        switch (tipo.toLowerCase()) {
            case "bioquimico":
                return new DatoBioquimico();
            // Agregamos más casos según los tipos de muestras que tengamos
            default:
                throw new IllegalArgumentException("Tipo de muestra no válido: " + tipo);
        }
    }
}

