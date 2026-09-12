package dev.isaac.digiloteca.controller;

import dev.isaac.digiloteca.dto.AtualizarStatusDoacaoRequest;
import dev.isaac.digiloteca.dto.CriarDoacaoRequest;
import dev.isaac.digiloteca.dto.DoacaoResponse;
import dev.isaac.digiloteca.service.DoacaoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doacoes")
public class DoacaoController {

    private final DoacaoService doacaoService;

    public DoacaoController(DoacaoService doacaoService) {
        this.doacaoService = doacaoService;
    }

    @PostMapping
    public ResponseEntity<DoacaoResponse> criar(
            @Valid @RequestBody CriarDoacaoRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(doacaoService.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<DoacaoResponse>> listar() {

        return ResponseEntity.ok(
                doacaoService.listarTodas()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoacaoResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                doacaoService.buscarPorId(id)
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<DoacaoResponse> atualizarStatus(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarStatusDoacaoRequest request) {

        return ResponseEntity.ok(
                doacaoService.atualizarStatus(id, request)
        );
    }
}