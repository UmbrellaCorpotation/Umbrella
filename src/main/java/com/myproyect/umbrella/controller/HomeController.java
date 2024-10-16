package com.myproyect.umbrella.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    // Este mapeo responderá a la raíz del contexto (por ejemplo, /app/)
    @GetMapping("/")
    public String home() {
        return "Bienvenido a mi aplicación Spring Boot!";
    }
}
