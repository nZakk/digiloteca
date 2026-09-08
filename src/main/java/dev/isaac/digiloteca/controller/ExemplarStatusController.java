package dev.isaac.digiloteca.controller;

import dev.isaac.digiloteca.dto.AtualizarStatusExemplarRequest;
import dev.isaac.digiloteca.dto.ExemplarResponse;
import dev.isaac.digiloteca.service.ExemplarService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exemplares")
public class ExemplarStatusController {
    
    private final ExemplarService exemplarService;

    public ExemplarStatusController(ExemplarService exemplarService) {
        this.exemplarService = exemplarService;
    }       

    @PatchMapping("/{id}/status")
    public ResponseEntity<ExemplarResponse> atualizarStatus(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarStatusExemplarRequest request) {

        ExemplarResponse exemplar = exemplarService.atualizarStatus(id, request);
        return ResponseEntity.ok(exemplar);
    }
}
