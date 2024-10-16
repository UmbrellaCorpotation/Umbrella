package com.myproyect.umbrella.config;


import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class ControllerConfig {

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        // Convierte cadenas vacías en null para evitar problemas de validación
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

}
