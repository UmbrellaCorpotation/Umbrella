package com.myproyect.umbrella.service;

import com.myproyect.umbrella.domain.Muestra;
import com.myproyect.umbrella.repos.MuestraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MuestraService {

    @Autowired
    private MuestraRepository muestraRepository;

    // Obtener todas las muestras
    public List<Muestra> getAllMuestras() {
        return muestraRepository.findAll();
    }

    // Obtener una muestra por ID
    public Muestra getMuestraById(Long id) {
        return muestraRepository.findById(id).orElse(null);
    }

    // Crear una nueva muestra
    public Muestra createMuestra(Muestra muestra) {
        return muestraRepository.save(muestra);
    }

    // Eliminar una muestra por ID
    public void deleteMuestra(Long id) {
        muestraRepository.deleteById(id);
    }

    // Buscar muestras por origen
    public List<Muestra> getMuestrasPorOrigen(String origen) {
        return muestraRepository.findByOrigen(origen);
    }

    // Buscar muestras obtenidas en una fecha específica
    public List<Muestra> getMuestrasPorFecha(Date fechaObtencion) {
        return muestraRepository.findByFechaObtencion(fechaObtencion);
    }

    // Buscar muestras cuya descripción contiene una palabra clave
    public List<Muestra> buscarMuestrasPorDescripcion(String keyword) {
        return muestraRepository.findByDescripcionContaining(keyword);
    }

    // Buscar muestras obtenidas entre dos fechas
    public List<Muestra> getMuestrasEntreFechas(Date startDate, Date endDate) {
        return muestraRepository.findByFechaObtencionBetween(startDate, endDate);
    }
}
