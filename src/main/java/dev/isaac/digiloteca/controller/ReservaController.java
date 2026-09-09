package dev.isaac.digiloteca.controller;

import dev.isaac.digiloteca.dto.CriarReservaRequest;
import dev.isaac.digiloteca.dto.ReservaResponse;
import dev.isaac.digiloteca.service.ReservaService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<ReservaResponse> criar(
            @Valid @RequestBody CriarReservaRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reservaService.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponse>> listar() {

        return ResponseEntity.ok(
                reservaService.listarTodas()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                reservaService.buscarPorId(id)
        );
    }
}