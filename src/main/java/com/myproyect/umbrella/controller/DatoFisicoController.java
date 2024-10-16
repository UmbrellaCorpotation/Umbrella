package com.myproyect.umbrella.controller;

import com.myproyect.umbrella.domain.DatoFisico;
import com.myproyect.umbrella.model.DatoFisicoDTO;
import com.myproyect.umbrella.service.DatoFisicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/datos-fisicos")
public class DatoFisicoController {

    @Autowired
    private DatoFisicoService datoFisicoService;

    @GetMapping
    public ResponseEntity<List<DatoFisicoDTO>> getAllDatosFisicos() {
        List<DatoFisico> datosFisicos = datoFisicoService.getAllDatosFisicos();
        List<DatoFisicoDTO> datosDTOs = datosFisicos.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(datosDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatoFisicoDTO> getDatoFisico(@PathVariable Long id) {
        DatoFisico dato = datoFisicoService.getDatoFisicoById(id);
        DatoFisicoDTO datoDTO = mapToDTO(dato);
        return ResponseEntity.ok(datoDTO);
    }

    @PostMapping
    public ResponseEntity<DatoFisicoDTO> createDatoFisico(@RequestBody DatoFisicoDTO datoDTO) {
        DatoFisico dato = datoFisicoService.createDatoFisico(mapToEntity(datoDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDTO(dato));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDatoFisico(@PathVariable Long id) {
        datoFisicoService.deleteDatoFisico(id);
        return ResponseEntity.noContent().build();
    }

    private DatoFisicoDTO mapToDTO(DatoFisico dato) {
        DatoFisicoDTO dto = new DatoFisicoDTO();
        dto.setId(dato.getId());
        dto.setMedida(dato.getMedida());
        dto.setValor(dato.getValor());
        dto.setUnidad(dato.getUnidad());
        return dto;
    }

    private DatoFisico mapToEntity(DatoFisicoDTO dto) {
        DatoFisico dato = new DatoFisico();
        dato.setMedida(dto.getMedida());
        dato.setValor(dto.getValor());
        dato.setUnidad(dto.getUnidad());
        return dato;
    }
}
