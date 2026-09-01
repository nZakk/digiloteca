package dev.isaac.digiloteca.controller;

import dev.isaac.digiloteca.model.Livro;
import dev.isaac.digiloteca.service.LivroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
