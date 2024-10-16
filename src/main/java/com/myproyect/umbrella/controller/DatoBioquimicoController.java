package com.myproyect.umbrella.controller;


import com.myproyect.umbrella.domain.DatoBioquimico;
import com.myproyect.umbrella.model.DatoBioquimicoDTO;
import com.myproyect.umbrella.service.DatoBioquimicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/datos-bioquimicos")
public class DatoBioquimicoController {

    @Autowired
    private DatoBioquimicoService datoBioquimicoService;

    @GetMapping
    public ResponseEntity<List<DatoBioquimicoDTO>> getAllDatosBioquimicos() {
        List<DatoBioquimico> datosBioquimicos = datoBioquimicoService.getAllDatosBioquimicos();
        List<DatoBioquimicoDTO> datosDTOs = datosBioquimicos.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(datosDTOs);
    }

    private DatoBioquimicoDTO mapToDTO(DatoBioquimico dato) {
        DatoBioquimicoDTO dto = new DatoBioquimicoDTO();
        dto.setId(dato.getId());
        dto.setCompuestoQuimico(dato.getCompuestoQuimico());
        dto.setConcentracion(dato.getConcentracion());
        return dto;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatoBioquimicoDTO> getDatoBioquimico(@PathVariable Long id) {
        DatoBioquimico dato = datoBioquimicoService.getDatoBioquimicoById(id);
        DatoBioquimicoDTO datoDTO = mapToDTO(dato);
        return ResponseEntity.ok(datoDTO);
    }

    @PostMapping
    public ResponseEntity<DatoBioquimicoDTO> createDatoBioquimico(@RequestBody DatoBioquimicoDTO datoDTO) {
        DatoBioquimico dato = datoBioquimicoService.createDatoBioquimico(mapToEntity(datoDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDTO(dato));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDatoBioquimico(@PathVariable Long id) {
        datoBioquimicoService.deleteDatoBioquimico(id);
        return ResponseEntity.noContent().build();
    }

    private DatoBioquimico mapToEntity(DatoBioquimicoDTO dto) {
        DatoBioquimico dato = new DatoBioquimico();
        dato.setCompuestoQuimico(dto.getCompuestoQuimico());
        dato.setConcentracion(dto.getConcentracion());
        return dato;
    }
}
