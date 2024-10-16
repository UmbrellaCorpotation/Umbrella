package com.myproyect.umbrella.repos;



import com.myproyect.umbrella.domain.DatoFisico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DatoFisicoRepository extends JpaRepository<DatoFisico, Long> {

    // Encuentra todos los datos físicos por la medida (por ejemplo, temperatura, presión)
    List<DatoFisico> findByMedida(String medida);

    // Encuentra todos los datos físicos con valores dentro de un rango
    List<DatoFisico> findByValorBetween(Double minValor, Double maxValor);

    // Encuentra todos los datos físicos por la unidad (por ejemplo, °C, atm)
    List<DatoFisico> findByUnidad(String unidad);

    // Encuentra todos los datos físicos por el origen de la muestra
    List<DatoFisico> findByOrigen(String origen);
}


