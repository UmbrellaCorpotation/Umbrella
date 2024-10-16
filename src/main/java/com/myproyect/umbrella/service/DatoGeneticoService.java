package com.myproyect.umbrella.service;


import com.myproyect.umbrella.domain.DatoGenetico;
import com.myproyect.umbrella.repos.DatoGeneticoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatoGeneticoService {

    @Autowired
    private DatoGeneticoRepository datoGeneticoRepository;

    // Obtener todos los datos genéticos
    public List<DatoGenetico> getAllDatosGeneticos() {
        return datoGeneticoRepository.findAll();
    }

    // Obtener un dato genético por ID
    public DatoGenetico getDatoGeneticoById(Long id) {
        return datoGeneticoRepository.findById(id).orElse(null);
    }

    // Crear un nuevo dato genético
    public DatoGenetico createDatoGenetico(DatoGenetico datoGenetico) {
        return datoGeneticoRepository.save(datoGenetico);
    }

    // Eliminar un dato genético por ID
    public void deleteDatoGenetico(Long id) {
        datoGeneticoRepository.deleteById(id);
    }

    // Buscar datos genéticos por secuencia de ADN
    public List<DatoGenetico> getDatosGeneticosPorSecuencia(String secuenciaADN) {
        return datoGeneticoRepository.findBySecuenciaADN(secuenciaADN);
    }

    // Buscar datos genéticos por observaciones
    public List<DatoGenetico> getDatosGeneticosPorObservaciones(String keyword) {
        return datoGeneticoRepository.findByObservacionesContaining(keyword);
    }
}
