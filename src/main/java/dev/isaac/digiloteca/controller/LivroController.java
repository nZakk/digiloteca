package dev.isaac.digiloteca.controller;

import dev.isaac.digiloteca.model.Livro;
import dev.isaac.digiloteca.service.LivroService;
import dev.isaac.digiloteca.dto.LivroDetalheResponse;
import dev.isaac.digiloteca.dto.LivroResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {
    
    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    public ResponseEntity<Livro> criar(@RequestBody Livro livro) {
        
        Livro livroSalvo = livroService.salvar(livro);

        return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(livroSalvo);
    }

    @GetMapping
    public ResponseEntity<List<LivroResponse>> listar() {
        List<LivroResponse> livros = livroService.listarTodos();
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroDetalheResponse> buscarPorId(@PathVariable Long id) {
        LivroDetalheResponse livro = livroService.buscarPorId(id);
        return ResponseEntity.ok(livro);
    }
}
