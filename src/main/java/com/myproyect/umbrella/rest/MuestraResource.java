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
    @ApiResponse(responseCode = "200", description = "Get a sample by ID")
    public ResponseEntity<DatoBioquimicoDTO> getMuestra(@PathVariable final Long id) {
        return ResponseEntity.ok(muestraService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201", description = "Create a new sample")
    public ResponseEntity<Long> createMuestra(@RequestBody final DatoBioquimicoDTO muestraDTO) {
        final Long createdId = muestraService.create(muestraDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Update an existing sample")
    public ResponseEntity<Void> updateMuestra(@PathVariable final Long id,
                                              @RequestBody final DatoBioquimicoDTO muestraDTO) {
        muestraService.update(id, muestraDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204", description = "Delete a sample")
    public ResponseEntity<Void> deleteMuestra(@PathVariable final Long id) {
        muestraService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
