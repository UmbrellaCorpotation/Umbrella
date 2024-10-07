package com.myproyect.umbrella.service;



import com.myproyect.umbrella.model.Evento;
import com.myproyect.umbrella.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public List<Evento> getAllEventos() {
        return eventoRepository.findAll();
    }

    public Optional<Evento> getEventoById(Integer id) {
        return eventoRepository.findById(id);
    }

    public Evento saveEvento(Evento evento) {
        return eventoRepository.save(evento);
    }

    public void deleteEvento(Integer id) {
        eventoRepository.deleteById(id);
    }

    public List<Evento> getEventosAfterFecha(LocalDateTime fecha) {
        return eventoRepository.findByFechaAfter(fecha);
    }
}
