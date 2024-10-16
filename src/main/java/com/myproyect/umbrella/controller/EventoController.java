package com.myproyect.umbrella.controller;


import com.myproyect.umbrella.model.Evento;
import com.myproyect.umbrella.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    // Obtener todos los eventos
    @GetMapping
    public List<Evento> getAllEventos() {
        return eventoService.getAllEventos();
    }

    // Obtener un evento por ID
    @GetMapping("/{id}")
    public ResponseEntity<Evento> getEventoById(@PathVariable Integer id) {
        Optional<Evento> evento = eventoService.getEventoById(id);
        return evento.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear un nuevo evento
    @PostMapping
    public Evento createEvento(@RequestBody Evento evento) {
        return eventoService.saveEvento(evento);
    }

    // Actualizar un evento existente
    @PutMapping("/{id}")
    public ResponseEntity<Evento> updateEvento(@PathVariable Integer id, @RequestBody Evento eventoDetails) {
        Optional<Evento> optionalEvento = eventoService.getEventoById(id);
        if (!optionalEvento.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Evento evento = optionalEvento.get();
        evento.setTipoEvento(eventoDetails.getTipoEvento());
        evento.setDescripcion(eventoDetails.getDescripcion());
        evento.setFecha(eventoDetails.getFecha());
        // Actualizar otras relaciones si es necesario
        Evento updatedEvento = eventoService.saveEvento(evento);
        return ResponseEntity.ok(updatedEvento);
    }

    // Eliminar un evento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable Integer id) {
        Optional<Evento> optionalEvento = eventoService.getEventoById(id);
        if (!optionalEvento.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        eventoService.deleteEvento(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener eventos después de una fecha específica
    @GetMapping("/after/{fecha}")
    public List<Evento> getEventosAfterFecha(@PathVariable String fecha) {
        LocalDateTime fechaLocal = LocalDateTime.parse(fecha);
        return eventoService.getEventosAfterFecha(fechaLocal);
    }
}
