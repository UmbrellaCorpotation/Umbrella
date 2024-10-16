package com.myproyect.umbrella.repos;




import com.myproyect.umbrella.domain.DatoBioquimico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



import java.util.List;

@Repository
public interface DatoBioquimicoRepository extends JpaRepository<DatoBioquimico, Long> {

    // Encuentra todos los datos bioquímicos por el compuesto químico
    List<DatoBioquimico> findByCompuestoQuimico(String compuestoQuimico);

    // Encuentra todos los datos bioquímicos cuya concentración esté dentro de un rango
    List<DatoBioquimico> findByConcentracionBetween(Double minConcentracion, Double maxConcentracion);

    // Encuentra todos los datos bioquímicos por el origen de la muestra (heredado de Muestra)
    List<DatoBioquimico> findByOrigen(String origen);
}
