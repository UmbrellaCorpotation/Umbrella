package com.myproyect.umbrella.repository;

import com.myproyect.umbrella.model.DatosFisicos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DatosFisicosRepository extends JpaRepository<DatosFisicos, Integer> {
    List<DatosFisicos> findByMuestraId(Integer muestraId);
}

