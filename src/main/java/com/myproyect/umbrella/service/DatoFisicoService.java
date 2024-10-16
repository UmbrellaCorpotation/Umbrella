package com.myproyect.umbrella.service;


import com.myproyect.umbrella.domain.DatoFisico;
import com.myproyect.umbrella.repos.DatoFisicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatoFisicoService {

    @Autowired
    private DatoFisicoRepository datoFisicoRepository;

    // Obtener todos los datos físicos
    public List<DatoFisico> getAllDatosFisicos() {
        return datoFisicoRepository.findAll();
    }

    // Obtener un dato físico por ID
    public DatoFisico getDatoFisicoById(Long id) {
        return datoFisicoRepository.findById(id).orElse(null);
    }

    // Crear un nuevo dato físico
    public DatoFisico createDatoFisico(DatoFisico datoFisico) {
        return datoFisicoRepository.save(datoFisico);
    }

    // Eliminar un dato físico por ID
    public void deleteDatoFisico(Long id) {
        datoFisicoRepository.deleteById(id);
    }


}
