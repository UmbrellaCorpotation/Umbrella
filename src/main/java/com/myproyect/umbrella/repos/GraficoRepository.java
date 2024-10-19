package com.myproyect.umbrella.repos;

import com.myproyect.umbrella.domain.Grafico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GraficoRepository extends JpaRepository<Grafico, Long> {
    // Buscar gráficos por el ID de grupoMuestras
    List<Grafico> findByGrupoMuestrasId(Long grupoMuestrasId);
}

