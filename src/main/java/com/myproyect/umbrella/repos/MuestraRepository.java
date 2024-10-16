package com.myproyect.umbrella.repos;



import com.myproyect.umbrella.domain.Muestra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MuestraRepository extends JpaRepository<Muestra, Long> {

    // Encuentra todas las muestras por su origen
    List<Muestra> findByOrigen(String origen);

    // Encuentra todas las muestras obtenidas en una fecha específica
    List<Muestra> findByFechaObtencion(Date fechaObtencion);

    // Encuentra todas las muestras cuya descripción contenga una palabra clave
    List<Muestra> findByDescripcionContaining(String keyword);

    // Encuentra todas las muestras obtenidas entre dos fechas
    List<Muestra> findByFechaObtencionBetween(Date startDate, Date endDate);

}
