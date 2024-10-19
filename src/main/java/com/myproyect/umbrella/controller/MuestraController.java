package com.myproyect.umbrella.controller;

import com.myproyect.umbrella.model.DatoBioquimicoDTO;
import com.myproyect.umbrella.service.MuestraService;
import com.myproyect.umbrella.util.MuestraNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/muestra")
public class MuestraController {

    @Autowired
    private MuestraService muestraService;

    /**
     * Obtener una muestra por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DatoBioquimicoDTO> getMuestra(@PathVariable Long id) {
        try {
            DatoBioquimicoDTO muestraDTO = muestraService.get(id);
            return ResponseEntity.ok(muestraDTO);
        } catch (MuestraNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crear una nueva muestra.
     */
    @PostMapping("/{tipo}")
    public ResponseEntity<Long> createMuestra(@PathVariable String tipo,
                                              @RequestBody DatoBioquimicoDTO muestraDTO) {
        try {
            Long id = muestraService.create(muestraDTO, tipo);
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Actualizar una muestra existente.
     */
    @PutMapping("/{id}/{tipo}")
    public ResponseEntity<Void> updateMuestra(@PathVariable Long id,
                                              @PathVariable String tipo,
                                              @RequestBody DatoBioquimicoDTO muestraDTO) {
        try {
            muestraService.update(id, muestraDTO, tipo);
            return ResponseEntity.ok().build();
        } catch (MuestraNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Eliminar una muestra.
     */
    @DeleteMapping("/{id}/{tipo}")
    public ResponseEntity<Void> deleteMuestra(@PathVariable Long id,
                                              @PathVariable String tipo) {
        try {
            muestraService.delete(id, tipo);
            return ResponseEntity.noContent().build();
        } catch (MuestraNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crear múltiples muestras en lote.
     */
    @PostMapping("/batch/{tipo}")
    public ResponseEntity<Void> createMuestrasBatch(@PathVariable String tipo,
                                                    @RequestBody List<DatoBioquimicoDTO> muestrasDTO) {
        try {
            muestraService.createAll(muestrasDTO, tipo);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Procesar y agrupar muestras concurrentemente.
     */
    @PostMapping("/procesar")
    public ResponseEntity<Void> procesarYAgruparMuestras() {
        muestraService.procesarYAgruparMuestras();
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{tipo}/all")
    public ResponseEntity<List<DatoBioquimicoDTO>> getAllMuestras(@PathVariable String tipo) {
        List<DatoBioquimicoDTO> muestrasDTO = muestraService.getAll(tipo);
        return ResponseEntity.ok(muestrasDTO);
    }
}
