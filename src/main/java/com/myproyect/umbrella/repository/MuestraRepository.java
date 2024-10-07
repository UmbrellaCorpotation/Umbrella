package com.myproyect.umbrella.repository;


import com.myproyect.umbrella.model.Muestra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MuestraRepository extends JpaRepository<Muestra, Integer> {
    // Métodos de consulta personalizados si es necesario
}
