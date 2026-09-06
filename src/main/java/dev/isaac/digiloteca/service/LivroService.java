package dev.isaac.digiloteca.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.isaac.digiloteca.dto.LivroResponse;
import dev.isaac.digiloteca.dto.LivroDetalheResponse;
import dev.isaac.digiloteca.dto.ExemplarResumoResponse;
import dev.isaac.digiloteca.model.Livro;
import dev.isaac.digiloteca.repository.ExemplarRepository;
import dev.isaac.digiloteca.repository.LivroRepository;


@Service
public class LivroService {
    
    private final LivroRepository livroRepository;
    private final ExemplarRepository exemplarRepository;

    public LivroService(LivroRepository livroRepository, ExemplarRepository exemplarRepository) {
        this.livroRepository = livroRepository;
        this.exemplarRepository = exemplarRepository;
    }

    public Livro salvar(Livro livro) {
        if (livroRepository.existsByIsbn(livro.getIsbn())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Livro com ISBN já existe");
        }

        return livroRepository.save(livro); 
    }

    public List<LivroResponse> listarTodos() {
        return livroRepository.findAll().stream()
                .map(livro -> new LivroResponse(
                    livro.getId(),
                    livro.getTitulo(),
                    livro.getAutor(),
                    livro.getEditora(),
                    livro.getIsbn(),
                    livro.getAnoPublicacao(),
                    livro.getCategorias(),
                    livro.getDescricao()
                ))
                .toList();
    }

    public LivroDetalheResponse buscarPorId(Long id) {
        Livro livro = livroRepository.findById(id)
            .orElseThrow(() -> 
                    new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Livro não encontrado")
            );

        List<ExemplarResumoResponse> exemplares = 
            exemplarRepository.findByLivroId(livro.getId())
            .stream()
            .map(exemplar -> new ExemplarResumoResponse(
                exemplar.getId(),
                exemplar.getCodigo(),
                exemplar.getStatus()
            )).toList();

            return new LivroDetalheResponse(
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getEditora(),
                livro.getIsbn(),
                livro.getAnoPublicacao(),
                livro.getCategorias(),
                livro.getDescricao(),
                exemplares
            );           
    }

}
