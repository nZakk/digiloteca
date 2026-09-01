package dev.isaac.digiloteca.service;

import dev.isaac.digiloteca.dto.CriarExemplarRequest;
import dev.isaac.digiloteca.dto.ExemplarResponse;
import dev.isaac.digiloteca.enums.StatusExemplar;
import dev.isaac.digiloteca.model.Exemplar;
import dev.isaac.digiloteca.model.Livro;
import dev.isaac.digiloteca.repository.ExemplarRepository;
import dev.isaac.digiloteca.repository.LivroRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ExemplarService {

    private final ExemplarRepository exemplarRepository;
    private final LivroRepository livroRepository;

    public ExemplarService(ExemplarRepository exemplarRepository, LivroRepository livroRepository) {
        this.exemplarRepository = exemplarRepository;
        this.livroRepository = livroRepository;
    }

    public ExemplarResponse criar(Long livroId, CriarExemplarRequest request) {
        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));

        if (exemplarRepository.existsByCodigo(request.getCodigo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Exemplar com código já existe");
        }

        Exemplar exemplar = new Exemplar();

        exemplar.setCodigo(request.getCodigo());
        exemplar.setStatus(StatusExemplar.DISPONIVEL);
        exemplar.setLivro(livro);

        Exemplar exemplarSalvo = exemplarRepository.save(exemplar);

        return new ExemplarResponse(
            exemplarSalvo.getId(),
            exemplarSalvo.getCodigo(),
            exemplarSalvo.getStatus(),
            livro.getId(),
            livro.getTitulo()
        );
    }

    
}
