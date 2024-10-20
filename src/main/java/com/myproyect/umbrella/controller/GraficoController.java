package com.myproyect.umbrella.controller;

import com.myproyect.umbrella.model.GraficoDTO;
import com.myproyect.umbrella.service.GraficoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/graficos")
public class GraficoController {

    @Autowired
    private GraficoService graficoService;

    /**
     * Obtener un gráfico por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GraficoDTO> getGrafico(@PathVariable Long id) {
        GraficoDTO graficoDTO = graficoService.getGrafico(id);
        if (graficoDTO != null) {
            return ResponseEntity.ok(graficoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crear un nuevo gráfico.
     */
    @PostMapping
    public ResponseEntity<Long> createGrafico(@RequestBody GraficoDTO graficoDTO) {
        Long id = graficoService.createGrafico(graficoDTO);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    /**
     * Actualizar un gráfico existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateGrafico(@PathVariable Long id, @RequestBody GraficoDTO graficoDTO) {
        try {
            graficoService.updateGrafico(id, graficoDTO);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Eliminar un gráfico por su ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrafico(@PathVariable Long id) {
        graficoService.deleteGrafico(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtener todos los gráficos.
     */
    @GetMapping("/obtener")
    public ResponseEntity<List<GraficoDTO>> getAllGraficos() {
        List<GraficoDTO> graficos = graficoService.getAllGraficos();
        return ResponseEntity.ok(graficos);
    }

    /**
     * Procesar gráficos concurrentemente.
     */
    @PostMapping("/procesar")
    public ResponseEntity<Void> procesarGraficos() {
        graficoService.procesarGraficosConcurrentemente();
        return ResponseEntity.accepted().build();
    }
}
