package com.myproyect.umbrella.repository;



import com.myproyect.umbrella.model.DatosBioquimicos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DatosBioquimicosRepository extends JpaRepository<DatosBioquimicos, Integer> {
    List<DatosBioquimicos> findByMuestraId(Integer muestraId);
}
