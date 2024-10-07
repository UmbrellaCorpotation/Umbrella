package com.myproyect.umbrella.repository;



import com.myproyect.umbrella.model.TipoDato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDatoRepository extends JpaRepository<TipoDato, Integer> {
    // Métodos de consulta personalizados si es necesario
}
