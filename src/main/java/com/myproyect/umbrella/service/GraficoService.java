package com.myproyect.umbrella.service;

import com.myproyect.umbrella.domain.DatoBioquimico;
import com.myproyect.umbrella.domain.Grafico;
import com.myproyect.umbrella.domain.GrupoMuestras;
import com.myproyect.umbrella.domain.Muestra;
import com.myproyect.umbrella.model.GraficoDTO;
import com.myproyect.umbrella.repos.GraficoRepository;
import com.myproyect.umbrella.repos.GrupoMuestrasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.Async;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class GraficoService {

    @Autowired
    private GraficoRepository graficoRepository;

    @Autowired
    private GrupoMuestrasRepository grupoMuestrasRepository;

    @Autowired
    private GrupoMuestraService grupoMuestraService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Transactional
    public Long createGrafico(GraficoDTO graficoDTO) {
        Grafico grafico = dtoToEntity(graficoDTO);
        graficoRepository.save(grafico);
        return grafico.getId();
    }

    @Transactional(readOnly = true)
    public GraficoDTO getGrafico(Long id) {
        Grafico grafico = graficoRepository.findById(id).orElse(null);
        return entityToDTO(grafico);
    }

    @Transactional
    public void updateGrafico(Long id, GraficoDTO graficoDTO) {
        Grafico grafico = graficoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gráfico no encontrado"));

        // Actualizar los datos del gráfico
        grafico.setDatosProcesados(graficoDTO.getDatosProcesados());
        grafico.setTimestamp(System.currentTimeMillis());

        graficoRepository.save(grafico);
    }

    @Transactional
    public void deleteGrafico(Long id) {
        graficoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<GraficoDTO> getAllGraficos() {
        List<Grafico> graficos = graficoRepository.findAll();
        return graficos.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    // Procesar los gráficos concurrentemente
    @Async
    public void procesarGraficosConcurrentemente() {
        List<GrupoMuestras> grupos = grupoMuestraService.findAllGrupos();

        // Usamos un CountDownLatch para sincronizar la finalización de los hilos
        CountDownLatch latch = new CountDownLatch(grupos.size());

        for (GrupoMuestras grupo : grupos) {
            executorService.submit(() -> {
                try {
                    // Procesar el grupo de muestras
                    procesarGrupo(grupo);
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            // Esperar a que todos los grupos sean procesados
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void procesarGrupo(GrupoMuestras grupo) {
        // Procesar las muestras del grupo usando ForkJoinPool
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Map<String, Double> datosProcesados = forkJoinPool.invoke(new ProcesarMuestrasTask(grupo.getMuestras()));

        // Crear o actualizar el gráfico de manera sincronizada
        synchronized (this) {
            Grafico grafico = new Grafico();
            grafico.setGrupoMuestras(grupo);
            grafico.setDatosProcesados(datosProcesados);
            grafico.setTimestamp(System.currentTimeMillis());

            graficoRepository.save(grafico);
        }
    }

    // Tarea recursiva para procesar muestras utilizando ForkJoinTask
    private class ProcesarMuestrasTask extends RecursiveTask<Map<String, Double>> {

        private static final int THRESHOLD = 10;
        private List<Muestra> muestras;

        public ProcesarMuestrasTask(List<Muestra> muestras) {
            this.muestras = muestras;
        }

        @Override
        protected Map<String, Double> compute() {
            if (muestras.size() <= THRESHOLD) {
                // Procesar secuencialmente si el tamaño es menor o igual al umbral
                return procesarMuestrasSecuencialmente(muestras);
            } else {
                // Dividir la tarea en dos subtareas
                int mid = muestras.size() / 2;
                ProcesarMuestrasTask task1 = new ProcesarMuestrasTask(muestras.subList(0, mid));
                ProcesarMuestrasTask task2 = new ProcesarMuestrasTask(muestras.subList(mid, muestras.size()));

                // Ejecutar las subtareas en paralelo
                invokeAll(task1, task2);

                // Combinar los resultados
                Map<String, Double> result1 = task1.join();
                Map<String, Double> result2 = task2.join();

                return combinarResultados(result1, result2);
            }
        }

        private Map<String, Double> procesarMuestrasSecuencialmente(List<Muestra> muestras) {
            Map<String, Double> datos = new HashMap<>();

            // Contar el número de muestras por categoría
            Map<String, Long> conteoPorCategoria = muestras.stream()
                    .filter(m -> m instanceof DatoBioquimico)
                    .map(m -> (DatoBioquimico) m)
                    .collect(Collectors.groupingBy(DatoBioquimico::getCategory, Collectors.counting()));

            // Calcular el promedio de potencia ("Strength") por categoría
            Map<String, Double> promedioPotenciaPorCategoria = muestras.stream()
                    .filter(m -> m instanceof DatoBioquimico)
                    .map(m -> (DatoBioquimico) m)
                    .collect(Collectors.groupingBy(DatoBioquimico::getCategory, Collectors.averagingDouble(GraficoService.this::parseStrength)));

            // Combinar los datos en un solo mapa
            conteoPorCategoria.forEach((categoria, conteo) -> {
                String clave = "Conteo " + categoria;
                datos.merge(clave, conteo.doubleValue(), Double::sum);
            });

            promedioPotenciaPorCategoria.forEach((categoria, promedio) -> {
                String clave = "Promedio Potencia " + categoria;
                datos.merge(clave, promedio, Double::sum);
            });

            return datos;
        }

        private Map<String, Double> combinarResultados(Map<String, Double> result1, Map<String, Double> result2) {
            Map<String, Double> combined = new HashMap<>(result1);

            result2.forEach((key, value) -> {
                combined.merge(key, value, (v1, v2) -> {
                    if (key.startsWith("Promedio Potencia")) {
                        // Calcular el promedio total
                        return (v1 + v2) / 2;
                    } else {
                        // Sumar los conteos
                        return v1 + v2;
                    }
                });
            });

            return combined;
        }
    }

    // Metodo para convertir el campo "Strength" a un valor numérico
    private double parseStrength(DatoBioquimico dato) {
        String strength = dato.getStrength(); // Ejemplo: "938 mg"
        if (strength != null && !strength.isEmpty()) {
            try {
                String numericPart = strength.split(" ")[0]; // Extrae "938" de "938 mg"
                return Double.parseDouble(numericPart);
            } catch (Exception e) {
                // Manejar posibles excepciones
                return 0.0;
            }
        }
        return 0.0;
    }

    private Grafico dtoToEntity(GraficoDTO dto) {
        Grafico grafico = new Grafico();
        grafico.setId(dto.getId());

        if (dto.getGrupoMuestrasId() != null) {
            GrupoMuestras grupo = grupoMuestrasRepository.findById(dto.getGrupoMuestrasId())
                    .orElseThrow(() -> new RuntimeException("GrupoMuestras no encontrado"));
            grafico.setGrupoMuestras(grupo);
        }

        grafico.setDatosProcesados(dto.getDatosProcesados());
        grafico.setTimestamp(dto.getTimestamp());
        return grafico;
    }

    private GraficoDTO entityToDTO(Grafico grafico) {
        if (grafico == null) {
            return null;
        }
        GraficoDTO dto = new GraficoDTO();
        dto.setId(grafico.getId());
        dto.setGrupoMuestrasId(grafico.getGrupoMuestras().getId());
        dto.setDatosProcesados(grafico.getDatosProcesados());
        dto.setTimestamp(grafico.getTimestamp());
        return dto;
    }
}

