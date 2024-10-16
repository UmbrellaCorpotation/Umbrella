package com.myproyect.umbrella.repos;

import com.myproyect.umbrella.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {
    List<Evento> findByFechaAfter(LocalDateTime fecha);
}
