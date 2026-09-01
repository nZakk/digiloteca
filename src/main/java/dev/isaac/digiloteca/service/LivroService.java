package dev.isaac.digiloteca.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.isaac.digiloteca.model.Livro;
import dev.isaac.digiloteca.repository.LivroRepository;


@Service
public class LivroService {
    
    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public Livro salvar(Livro livro) {
        if (livroRepository.existsByIsbn(livro.getIsbn())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Livro com ISBN já existe");
        }

        return livroRepository.save(livro); 
    }


}
