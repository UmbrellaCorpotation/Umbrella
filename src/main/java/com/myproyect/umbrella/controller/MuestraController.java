package com.myproyect.umbrella.controller;

import com.myproyect.umbrella.domain.*;
import com.myproyect.umbrella.model.MuestraDTO;
import com.myproyect.umbrella.service.MuestraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/muestras")
public class MuestraController {

    @Autowired
    private MuestraService muestraService;








}
