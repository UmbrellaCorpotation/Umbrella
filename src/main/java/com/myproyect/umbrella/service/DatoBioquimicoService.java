package com.myproyect.umbrella.service;

import com.myproyect.umbrella.domain.DatoBioquimico;
import com.myproyect.umbrella.repos.DatoBioquimicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatoBioquimicoService {

    @Autowired
    private DatoBioquimicoRepository datoBioquimicoRepository;

    // Obtener todos los datos bioquímicos
    public List<DatoBioquimico> getAllDatosBioquimicos() {
        return datoBioquimicoRepository.findAll();
    }

    // Obtener un dato bioquímico por ID
    public DatoBioquimico getDatoBioquimicoById(Long id) {
        return datoBioquimicoRepository.findById(id).orElse(null);
    }

    // Crear un nuevo dato bioquímico
    public DatoBioquimico createDatoBioquimico(DatoBioquimico datoBioquimico) {
        return datoBioquimicoRepository.save(datoBioquimico);
    }

    // Eliminar un dato bioquímico por ID
    public void deleteDatoBioquimico(Long id) {
        datoBioquimicoRepository.deleteById(id);
    }



}
