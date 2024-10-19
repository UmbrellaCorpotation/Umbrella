package com.myproyect.umbrella.service;

import com.myproyect.umbrella.domain.GrupoMuestras;
import com.myproyect.umbrella.repos.GrupoMuestrasRepository;
import com.myproyect.umbrella.util.GrupoMuestrasNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.*;
import java.util.concurrent.*;

@Service
public class GrupoMuestraService {

    private final GrupoMuestrasRepository grupoMuestrasRepository;

    // ExecutorService para manejar la concurrencia
    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Autowired
    public GrupoMuestraService(GrupoMuestrasRepository grupoMuestrasRepository) {
        this.grupoMuestrasRepository = grupoMuestrasRepository;
    }

    // Método para crear un nuevo GrupoMuestras
    @Transactional
    public Long create(GrupoMuestras grupoMuestras) {
        GrupoMuestras savedGrupo = grupoMuestrasRepository.save(grupoMuestras);
        return savedGrupo.getId();
    }

    // Método para obtener un GrupoMuestras por su ID
    @Transactional(readOnly = true)
    public GrupoMuestras get(Long id) {
        return grupoMuestrasRepository.findById(id)
                .orElseThrow(() -> new GrupoMuestrasNotFoundException(id));
    }

    // Método para actualizar un GrupoMuestras existente
    @Transactional
    public void update(Long id, GrupoMuestras grupoMuestras) {
        GrupoMuestras existingGrupo = grupoMuestrasRepository.findById(id)
                .orElseThrow(() -> new GrupoMuestrasNotFoundException(id));

        // Si necesitas actualizar las muestras dentro del grupo, maneja eso aquí

        grupoMuestrasRepository.save(existingGrupo);
    }

    // Método para eliminar un GrupoMuestras por su ID
    @Transactional
    public void delete(Long id) {
        if (grupoMuestrasRepository.existsById(id)) {
            grupoMuestrasRepository.deleteById(id);
        } else {
            throw new GrupoMuestrasNotFoundException(id);
        }
    }

    @Transactional(readOnly = true)
    public List<GrupoMuestras> findAllGrupos() {
        try {
            // Obtener todos los IDs de GrupoMuestras
            List<Long> allIds = grupoMuestrasRepository.findAllIds();

            // Definir el tamaño de cada lote
            int batchSize = 100; // Puedes ajustar este valor según tus necesidades
            int totalGrupos = allIds.size();
            int numBatches = (int) Math.ceil((double) totalGrupos / batchSize);

            List<Callable<List<GrupoMuestras>>> tasks = new ArrayList<>();

            for (int i = 0; i < numBatches; i++) {
                int start = i * batchSize;
                int end = Math.min(start + batchSize, totalGrupos);
                List<Long> batchIds = allIds.subList(start, end);

                tasks.add(() -> {
                    // Recuperar los GrupoMuestras por sus IDs
                    return grupoMuestrasRepository.findAllById(batchIds);
                });
            }

            List<Future<List<GrupoMuestras>>> futures = executorService.invokeAll(tasks);
            List<GrupoMuestras> allGrupos = new ArrayList<>();

            for (Future<List<GrupoMuestras>> future : futures) {
                allGrupos.addAll(future.get());
            }

            return allGrupos;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error al recuperar los grupos de muestras", e);
        }
    }

}
