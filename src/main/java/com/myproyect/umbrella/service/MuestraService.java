package com.myproyect.umbrella.service;

import com.myproyect.umbrella.domain.*;
import com.myproyect.umbrella.model.DatoBioquimicoDTO;
import com.myproyect.umbrella.repos.*;
import com.myproyect.umbrella.util.MuestraNotFoundException;
import com.myproyect.umbrella.service.MuestraFactoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.Async;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class MuestraService {

    private final DatoBioquimicoRepository datoBioquimicoRepository;
    private final GrupoMuestrasRepository grupoMuestrasRepository;
    private final MuestraFactoryService muestraFactoryService;

    // ExecutorService para manejar la concurrencia
    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Autowired
    public MuestraService(DatoBioquimicoRepository datoBioquimicoRepository,
                          GrupoMuestrasRepository grupoMuestrasRepository,
                          MuestraFactoryService muestraFactoryService) {
        this.datoBioquimicoRepository = datoBioquimicoRepository;
        this.grupoMuestrasRepository = grupoMuestrasRepository;
        this.muestraFactoryService = muestraFactoryService;
    }

    @Transactional(readOnly = true)
    public DatoBioquimicoDTO get(Long id) {
        Optional<DatoBioquimico> datoBioquimico = datoBioquimicoRepository.findById(id);
        return datoBioquimico.map(this::bioquimicoToDTO)
                .orElseThrow(() -> new MuestraNotFoundException(id));
    }

    // Crear una nueva muestra con concurrencia utilizando ExecutorService y Factory
    @Transactional
    public Long create(DatoBioquimicoDTO muestraDTO, String tipo) {
        Future<Long> future = executorService.submit(() -> {
            // Utilizar la fábrica para crear la muestra
            Muestra muestra = muestraFactoryService.crearMuestra(tipo);

            // Mapear los datos del DTO a la muestra creada
            if (muestra instanceof DatoBioquimico) {
                DatoBioquimico datoBioquimico = (DatoBioquimico) muestra;
                datoBioquimico = dtoToBioquimico(muestraDTO, datoBioquimico);
                DatoBioquimico savedMuestra = datoBioquimicoRepository.save(datoBioquimico);
                return savedMuestra.getId();
            } else {
                throw new IllegalArgumentException("Tipo de muestra no soportado para este DTO");
            }
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error al crear la muestra", e);
        }
    }

    // Método para actualizar una muestra existente
    @Transactional
    public void update(Long id, DatoBioquimicoDTO muestraDTO, String tipo) {
        // Utilizar la fábrica para crear una instancia del tipo de muestra
        Muestra muestra = muestraFactoryService.crearMuestra(tipo);

        if (muestra instanceof DatoBioquimico) {
            DatoBioquimico existingMuestra = datoBioquimicoRepository.findById(id)
                    .orElseThrow(() -> new MuestraNotFoundException(id));

            // Mapear los datos del DTO a la muestra existente
            DatoBioquimico updatedMuestra = dtoToBioquimico(muestraDTO, existingMuestra);

            // Guardar la muestra actualizada
            datoBioquimicoRepository.save(updatedMuestra);
        } else {
            throw new IllegalArgumentException("Tipo de muestra no soportado para este DTO");
        }
    }

    // Método para eliminar una muestra por su ID
    @Transactional
    public void delete(Long id, String tipo) {
        // Utilizar la fábrica para determinar el tipo de muestra
        Muestra muestra = muestraFactoryService.crearMuestra(tipo);

        if (muestra instanceof DatoBioquimico) {
            boolean existsInBioquimico = datoBioquimicoRepository.existsById(id);
            if (existsInBioquimico) {
                datoBioquimicoRepository.deleteById(id);
            } else {
                throw new MuestraNotFoundException(id);
            }
        } else {
            throw new IllegalArgumentException("Tipo de muestra no soportado");
        }
    }


    // Crear múltiples muestras con concurrencia utilizando Factory
    @Transactional
    public void createAll(List<DatoBioquimicoDTO> muestrasDTO, String tipo) {
        List<Callable<DatoBioquimico>> tasks = muestrasDTO.stream()
                .map(dto -> (Callable<DatoBioquimico>) () -> {
                    // Utilizar la fábrica para crear la muestra
                    Muestra muestra = muestraFactoryService.crearMuestra(tipo);
                    if (muestra instanceof DatoBioquimico) {
                        DatoBioquimico datoBioquimico = (DatoBioquimico) muestra;
                        return dtoToBioquimico(dto, datoBioquimico);
                    } else {
                        throw new IllegalArgumentException("Tipo de muestra no soportado para este DTO");
                    }
                })
                .collect(Collectors.toList());

        try {
            List<Future<DatoBioquimico>> futures = executorService.invokeAll(tasks);
            List<DatoBioquimico> muestras = new ArrayList<>();
            for (Future<DatoBioquimico> future : futures) {
                muestras.add(future.get());
            }
            datoBioquimicoRepository.saveAll(muestras);
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error al crear las muestras", e);
        }
    }

    // Método para procesar todas las muestras concurrentemente y agruparlas
    @Async
    public void procesarYAgruparMuestras() {
        // Paso 1: Obtener todas las muestras de la base de datos
        List<DatoBioquimico> muestras = datoBioquimicoRepository.findAll();

        // Paso 2: Procesar las muestras concurrentemente utilizando ForkJoinPool
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        List<DatoBioquimico> muestrasProcesadas = forkJoinPool.invoke(new ProcesarMuestrasTask(muestras));

        // Paso 3: Agrupar las muestras en grupos de 50
        List<GrupoMuestras> grupos = agruparMuestras(muestrasProcesadas);

        // Paso 4: Guardar los grupos en la base de datos
        grupos.forEach(grupoMuestrasRepository::save);
    }

    // Tarea recursiva para procesar muestras utilizando ForkJoinTask
    private class ProcesarMuestrasTask extends RecursiveTask<List<DatoBioquimico>> {

        private static final int THRESHOLD = 50;
        private List<DatoBioquimico> muestras;

        public ProcesarMuestrasTask(List<DatoBioquimico> muestras) {
            this.muestras = muestras;
        }

        @Override
        protected List<DatoBioquimico> compute() {
            if (muestras.size() <= THRESHOLD) {
                return procesarMuestrasSecuencialmente(muestras);
            } else {
                int mid = muestras.size() / 2;
                ProcesarMuestrasTask task1 = new ProcesarMuestrasTask(muestras.subList(0, mid));
                ProcesarMuestrasTask task2 = new ProcesarMuestrasTask(muestras.subList(mid, muestras.size()));

                invokeAll(task1, task2);

                List<DatoBioquimico> result1 = task1.join();
                List<DatoBioquimico> result2 = task2.join();

                List<DatoBioquimico> combined = new ArrayList<>();
                combined.addAll(result1);
                combined.addAll(result2);

                return combined;
            }
        }

        private List<DatoBioquimico> procesarMuestrasSecuencialmente(List<DatoBioquimico> muestras) {
            List<DatoBioquimico> procesadas = new ArrayList<>();

            muestras.forEach(muestra -> {
                DatoBioquimico muestraProcesada = procesarMuestra(muestra);
                procesadas.add(muestraProcesada);
            });

            return procesadas;
        }

        private DatoBioquimico procesarMuestra(DatoBioquimico muestra) {
            // Implementa la lógica de procesamiento de una muestra
            muestra.setName(muestra.getName() + " - Procesada");
            return muestra;
        }
    }

    @Transactional
    public List<GrupoMuestras> agruparMuestras(List<DatoBioquimico> muestras) {
        List<GrupoMuestras> grupos = new ArrayList<>();

        int totalMuestras = muestras.size();
        int numGrupos = (int) Math.ceil((double) totalMuestras / 50);

        // Usamos ExecutorService para crear los grupos concurrentemente
        List<Future<GrupoMuestras>> futures = new ArrayList<>();

        for (int i = 0; i < numGrupos; i++) {
            int index = i;
            int start = i * 50;
            int end = Math.min(start + 50, totalMuestras);
            List<DatoBioquimico> sublist = muestras.subList(start, end);

            Future<GrupoMuestras> future = executorService.submit(() -> {
                GrupoMuestras grupo = new GrupoMuestras();

                // Sincronización al agregar muestras al grupo
                synchronized (grupo) {
                    sublist.forEach(grupo::addMuestra);
                }

                return grupo;
            });

            futures.add(future);
        }

        // Recopilar los resultados
        for (Future<GrupoMuestras> future : futures) {
            try {
                grupos.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }

        return grupos;
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

    private DatoBioquimico dtoToBioquimico(DatoBioquimicoDTO dto, DatoBioquimico muestra) {
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
