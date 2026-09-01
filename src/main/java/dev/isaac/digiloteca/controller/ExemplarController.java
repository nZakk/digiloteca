package dev.isaac.digiloteca.controller;

import dev.isaac.digiloteca.dto.CriarExemplarRequest;
import dev.isaac.digiloteca.dto.ExemplarResponse;
import dev.isaac.digiloteca.service.ExemplarService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/livros/{livroId}/exemplares")
public class ExemplarController {

    private final ExemplarService exemplarService;

    public ExemplarController(ExemplarService exemplarService) {
        this.exemplarService = exemplarService;
    }

    @PostMapping
    public ResponseEntity<ExemplarResponse> criar
            (@PathVariable Long livroId, 
            @Valid @RequestBody CriarExemplarRequest request) {

        ExemplarResponse exemplar = exemplarService.criar(livroId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(exemplar);
    }
    
}
