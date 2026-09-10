package dev.isaac.digiloteca.controller;

import dev.isaac.digiloteca.dto.CriarEmprestimoRequest;
import dev.isaac.digiloteca.dto.EmprestimoResponse;
import dev.isaac.digiloteca.service.EmprestimoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(
            EmprestimoService emprestimoService) {

        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    public ResponseEntity<EmprestimoResponse> criar(
            @Valid @RequestBody CriarEmprestimoRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(emprestimoService.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<EmprestimoResponse>> listar() {

        return ResponseEntity.ok(
                emprestimoService.listarTodos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                emprestimoService.buscarPorId(id)
        );
    }

    @PatchMapping("/{id}/devolucao")
    public ResponseEntity<EmprestimoResponse> devolver(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                emprestimoService.devolver(id)
        );
    }
}