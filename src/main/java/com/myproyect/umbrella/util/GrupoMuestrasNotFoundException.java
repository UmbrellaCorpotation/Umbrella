package com.myproyect.umbrella.util;

public class GrupoMuestrasNotFoundException extends RuntimeException {
    public GrupoMuestrasNotFoundException(Long id) {
        super("No se encontró el GrupoMuestras con ID " + id);
    }
}
