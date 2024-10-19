package com.myproyect.umbrella.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
public class HomeController {

    @GetMapping("/")  // O mantener "/"
    public String home() {
        return "home";
    }
}