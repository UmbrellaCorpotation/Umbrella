package com.myproyect.umbrella.controller;

import com.myproyect.umbrella.domain.DatoGenetico;
import com.myproyect.umbrella.model.DatoGeneticoDTO;
import com.myproyect.umbrella.service.DatoGeneticoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/datos-geneticos")
public class DatoGeneticoController {

    @Autowired
    private DatoGeneticoService datoGeneticoService;

    @GetMapping
    public ResponseEntity<List<DatoGeneticoDTO>> getAllDatosGeneticos() {
        List<DatoGenetico> datosGeneticos = datoGeneticoService.getAllDatosGeneticos();
        List<DatoGeneticoDTO> datosDTOs = datosGeneticos.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(datosDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatoGeneticoDTO> getDatoGenetico(@PathVariable Long id) {
        DatoGenetico dato = datoGeneticoService.getDatoGeneticoById(id);
        DatoGeneticoDTO datoDTO = mapToDTO(dato);
        return ResponseEntity.ok(datoDTO);
    }

    @PostMapping
    public ResponseEntity<DatoGeneticoDTO> createDatoGenetico(@RequestBody DatoGeneticoDTO datoDTO) {
        DatoGenetico dato = datoGeneticoService.createDatoGenetico(mapToEntity(datoDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDTO(dato));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDatoGenetico(@PathVariable Long id) {
        datoGeneticoService.deleteDatoGenetico(id);
        return ResponseEntity.noContent().build();
    }

    private DatoGeneticoDTO mapToDTO(DatoGenetico dato) {
        DatoGeneticoDTO dto = new DatoGeneticoDTO();
        dto.setId(dato.getId());
        dto.setSecuenciaADN(dato.getSecuenciaADN());
        dto.setObservaciones(dato.getObservaciones());
        return dto;
    }

    private DatoGenetico mapToEntity(DatoGeneticoDTO dto) {
        DatoGenetico dato = new DatoGenetico();
        dato.setSecuenciaADN(dto.getSecuenciaADN());
        dato.setObservaciones(dto.getObservaciones());
        return dato;
    }
}

