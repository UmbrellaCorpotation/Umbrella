package com.myproyect.umbrella.util;

public class MuestraNotFoundException extends RuntimeException {

    public MuestraNotFoundException(Long id) {
        super("Muestra no encontrada con ID: " + id);
    }

    public MuestraNotFoundException(String message) {
        super(message);
    }
}
