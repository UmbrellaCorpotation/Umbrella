package com.myproyect.umbrella.service;

import com.myproyect.umbrella.domain.DatoBioquimico;
import com.myproyect.umbrella.repos.DatoBioquimicoRepository;
import com.myproyect.umbrella.util.MuestraNotFoundException;
import com.myproyect.umbrella.model.DatoBioquimicoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MuestraService {

    private final DatoBioquimicoRepository datoBioquimicoRepository;

    @Autowired
    public MuestraService(DatoBioquimicoRepository datoBioquimicoRepository) {
        this.datoBioquimicoRepository = datoBioquimicoRepository;
    }

    // Obtener una muestra específica por ID
    public DatoBioquimicoDTO get(Long id) {
        Optional<DatoBioquimico> datoBioquimico = datoBioquimicoRepository.findById(id);
        if (datoBioquimico.isPresent()) {
            return bioquimicoToDTO(datoBioquimico.get());
        }
        throw new MuestraNotFoundException(id);
    }

    // Crear una nueva muestra
    @Transactional
    public Long create(DatoBioquimicoDTO muestraDTO) {
        DatoBioquimico muestra = dtoToBioquimico(muestraDTO);
        DatoBioquimico savedMuestra = datoBioquimicoRepository.save(muestra);
        return savedMuestra.getId();
    }

    // Actualizar una muestra existente
    @Transactional
    public void update(Long id, DatoBioquimicoDTO muestraDTO) {
        DatoBioquimico existingMuestra = datoBioquimicoRepository.findById(id)
                .orElseThrow(() -> new MuestraNotFoundException(id));
        DatoBioquimico updatedMuestra = dtoToBioquimico(muestraDTO);
        updatedMuestra.setId(existingMuestra.getId());
        datoBioquimicoRepository.save(updatedMuestra);
    }

    // Eliminar una muestra por su ID
    @Transactional
    public void delete(Long id) {
        boolean existsInBioquimico = datoBioquimicoRepository.existsById(id);
        if (existsInBioquimico) {
            datoBioquimicoRepository.deleteById(id);
        } else {
            throw new MuestraNotFoundException(id);
        }
    }

    // Métodos auxiliares para mapear entre entidades y DTOs

    private DatoBioquimicoDTO bioquimicoToDTO(DatoBioquimico muestra) {
        DatoBioquimicoDTO dto = new DatoBioquimicoDTO();
        dto.setId(muestra.getId());
        dto.setName(muestra.getName());
        dto.setCategory(muestra.getCategory());
        dto.setDosageForm(muestra.getDosageForm());
        dto.setStrength(muestra.getStrength());
        dto.setManufacturer(muestra.getManufacturer());
        dto.setIndication(muestra.getIndication());
        dto.setClassification(muestra.getClassification());
        return dto;
    }

    @Transactional
    public void createAll(List<DatoBioquimicoDTO> muestrasDTO) {
        List<DatoBioquimico> muestras = muestrasDTO.stream()
                .map(this::dtoToBioquimico)
                .collect(Collectors.toList());
        datoBioquimicoRepository.saveAll(muestras);
    }

    private DatoBioquimico dtoToBioquimico(DatoBioquimicoDTO dto) {
        DatoBioquimico muestra = new DatoBioquimico();
        muestra.setName(dto.getName());
        muestra.setCategory(dto.getCategory());
        muestra.setDosageForm(dto.getDosageForm());
        muestra.setStrength(dto.getStrength());
        muestra.setManufacturer(dto.getManufacturer());
        muestra.setIndication(dto.getIndication());
        muestra.setClassification(dto.getClassification());
        return muestra;
    }
}
