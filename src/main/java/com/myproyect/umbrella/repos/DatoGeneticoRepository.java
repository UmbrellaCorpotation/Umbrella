package com.myproyect.umbrella.repos;





import com.myproyect.umbrella.domain.DatoGenetico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface DatoGeneticoRepository extends JpaRepository<DatoGenetico, Long> {

    // Encuentra todos los datos genéticos por secuencia de ADN
    List<DatoGenetico> findBySecuenciaADN(String secuenciaADN);

    // Encuentra todos los datos genéticos por observaciones
    List<DatoGenetico> findByObservacionesContaining(String keyword);

    // Encuentra todos los datos genéticos por el origen de la muestra
    List<DatoGenetico> findByOrigen(String origen);
}
