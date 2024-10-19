package com.myproyect.umbrella.rest;

import com.myproyect.umbrella.model.DatoBioquimicoDTO;
import com.myproyect.umbrella.service.MuestraService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/muestras")
public class MuestraResource {

    private final MuestraService muestraService;

    public MuestraResource(final MuestraService muestraService) {
        this.muestraService = muestraService;
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Obtener una muestra por ID")
    public ResponseEntity<DatoBioquimicoDTO> getMuestra(@PathVariable final Long id) {
        return ResponseEntity.ok(muestraService.get(id));
    }

    @PostMapping("/{tipo}")
    @ApiResponse(responseCode = "201", description = "Crear una nueva muestra")
    public ResponseEntity<Long> createMuestra(@PathVariable String tipo,
                                              @RequestBody final DatoBioquimicoDTO muestraDTO) {
        final Long createdId = muestraService.create(muestraDTO, tipo);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PostMapping("/batch/{tipo}")
    @ApiResponse(responseCode = "201", description = "Crear nuevas muestras en lote")
    public ResponseEntity<Void> createMuestrasBatch(@PathVariable String tipo,
                                                    @RequestBody List<DatoBioquimicoDTO> muestrasDTO) {
        muestraService.createAll(muestrasDTO, tipo);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}/{tipo}")
    @ApiResponse(responseCode = "200", description = "Actualizar una muestra existente")
    public ResponseEntity<Void> updateMuestra(@PathVariable final Long id,
                                              @PathVariable String tipo,
                                              @RequestBody final DatoBioquimicoDTO muestraDTO) {
        muestraService.update(id, muestraDTO, tipo);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/{tipo}")
    @ApiResponse(responseCode = "204", description = "Eliminar una muestra")
    public ResponseEntity<Void> deleteMuestra(@PathVariable final Long id,
                                              @PathVariable String tipo) {
        muestraService.delete(id, tipo);
        return ResponseEntity.noContent().build();
    }

}

