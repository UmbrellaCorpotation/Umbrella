package com.myproyect.umbrella.repository;



import com.myproyect.umbrella.model.DatosGeneticos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DatosGeneticosRepository extends JpaRepository<DatosGeneticos, Integer> {
    List<DatosGeneticos> findByMuestraId(Integer muestraId);
}

