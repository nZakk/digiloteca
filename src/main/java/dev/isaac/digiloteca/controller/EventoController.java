package dev.isaac.digiloteca.controller;

import dev.isaac.digiloteca.dto.CriarEventoRequest;
import dev.isaac.digiloteca.dto.EventoResponse;
import dev.isaac.digiloteca.service.EventoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping
    public ResponseEntity<EventoResponse> criar(
            @Valid @RequestBody CriarEventoRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(eventoService.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<EventoResponse>> listar() {

        return ResponseEntity.ok(
                eventoService.listarTodos()
        );
    }

    @GetMapping("/proximos")
    public ResponseEntity<List<EventoResponse>> listarProximos() {

        return ResponseEntity.ok(
                eventoService.listarProximos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                eventoService.buscarPorId(id)
        );
    }
}