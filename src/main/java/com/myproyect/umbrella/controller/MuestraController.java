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

    @GetMapping
    public ResponseEntity<List<MuestraDTO>> getAllMuestras() {
        List<Muestra> muestras = muestraService.getAllMuestras();
        List<MuestraDTO> muestraDTOs = muestras.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(muestraDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MuestraDTO> getMuestra(@PathVariable Long id) {
        Muestra muestra = muestraService.getMuestraById(id);
        MuestraDTO muestraDTO = mapToDTO(muestra);
        return ResponseEntity.ok(muestraDTO);
    }

    @PostMapping
    public ResponseEntity<MuestraDTO> createMuestra(@RequestBody MuestraDTO muestraDTO) {
        Muestra muestra = mapToEntity(muestraDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDTO(muestraService.createMuestra(muestra)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMuestra(@PathVariable Long id) {
        muestraService.deleteMuestra(id);
        return ResponseEntity.noContent().build();
    }

    // Mapeo de Muestra a MuestraDTO
    private MuestraDTO mapToDTO(Muestra muestra) {
        MuestraDTO dto = new MuestraDTO();
        dto.setId(muestra.getId());
        dto.setFechaObtencion(muestra.getFechaObtencion());
        dto.setOrigen(muestra.getOrigen());
        dto.setDescripcion(muestra.getDescripcion());

        // Determinamos el tipo en función de la subclase
        if (muestra instanceof DatoGenetico) {
            dto.setTipo("genetica");
        } else if (muestra instanceof DatoBioquimico) {
            dto.setTipo("bioquimica");
        } else if (muestra instanceof DatoFisico) {
            dto.setTipo("fisica");
        }

        return dto;
    }

    // Mapeo de MuestraDTO a la entidad correspondiente
    private Muestra mapToEntity(MuestraDTO dto) {
        Muestra muestra;
        switch (dto.getTipo().toLowerCase()) {
            case "genetica":
                muestra = new DatoGenetico();  // Instancia de DatoGenetico
                break;
            case "bioquimica":
                muestra = new DatoBioquimico();  // Instancia de DatoBioquimico
                break;
            case "fisica":
                muestra = new DatoFisico();  // Instancia de DatoFisico
                break;
            default:
                throw new IllegalArgumentException("Tipo de muestra no soportado: " + dto.getTipo());
        }

        muestra.setFechaObtencion(dto.getFechaObtencion());
        muestra.setOrigen(dto.getOrigen());
        muestra.setDescripcion(dto.getDescripcion());

        return muestra;
    }
}
