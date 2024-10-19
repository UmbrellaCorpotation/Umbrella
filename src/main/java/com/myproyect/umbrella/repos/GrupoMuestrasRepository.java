package com.myproyect.umbrella.repos;

import com.myproyect.umbrella.domain.GrupoMuestras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GrupoMuestrasRepository extends JpaRepository<GrupoMuestras, Long> {

    @Query("SELECT g.id FROM GrupoMuestras g")
    List<Long> findAllIds();
}
