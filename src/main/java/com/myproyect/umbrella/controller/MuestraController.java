package com.myproyect.umbrella.controller;

import com.myproyect.umbrella.model.Muestra;
import com.myproyect.umbrella.service.MuestraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/muestras")
public class MuestraController {

    @Autowired
    private MuestraService muestraService;

    // Obtener todas las muestras
    @GetMapping
    public List<Muestra> getAllMuestras() {
        return muestraService.getAllMuestras();
    }

    // Obtener una muestra por ID
    @GetMapping("/{id}")
    public ResponseEntity<Muestra> getMuestraById(@PathVariable Integer id) {
        Optional<Muestra> muestra = muestraService.getMuestraById(id);
        return muestra.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear una nueva muestra
    @PostMapping
    public Muestra createMuestra(@RequestBody Muestra muestra) {
        return muestraService.saveMuestra(muestra);
    }

    // Actualizar una muestra existente
    @PutMapping("/{id}")
    public ResponseEntity<Muestra> updateMuestra(@PathVariable Integer id, @RequestBody Muestra muestraDetails) {
        Optional<Muestra> optionalMuestra = muestraService.getMuestraById(id);
        if (!optionalMuestra.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Muestra muestra = optionalMuestra.get();
        muestra.setCodigo(muestraDetails.getCodigo());
        muestra.setFechaRecepcion(muestraDetails.getFechaRecepcion());
        muestra.setDescripcion(muestraDetails.getDescripcion());
        // Actualizar otras relaciones si es necesario
        Muestra updatedMuestra = muestraService.saveMuestra(muestra);
        return ResponseEntity.ok(updatedMuestra);
    }

    // Eliminar una muestra
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMuestra(@PathVariable Integer id) {
        Optional<Muestra> optionalMuestra = muestraService.getMuestraById(id);
        if (!optionalMuestra.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        muestraService.deleteMuestra(id);
        return ResponseEntity.noContent().build();
    }
}

