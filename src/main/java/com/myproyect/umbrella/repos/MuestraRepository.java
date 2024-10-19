package com.myproyect.umbrella.repos;



import com.myproyect.umbrella.domain.Muestra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MuestraRepository extends JpaRepository<Muestra, Long> {
}
