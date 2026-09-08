package dev.isaac.digiloteca.repository;

import dev.isaac.digiloteca.enums.StatusExemplar;
import dev.isaac.digiloteca.model.Exemplar;
import dev.isaac.digiloteca.model.Livro;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
public class ExemplarRepositoryTest {
    
    @Autowired 
    private ExemplarRepository exemplarRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Test
    void testSaveExemplar() {
        Livro livro = new Livro();
        livro.setTitulo("titulo-test");
        livro.setAutor("autor-test");
        livro.setIsbn("isbn-test");

        Livro savedLivro = livroRepository.save(livro);

        Exemplar exemplar = new Exemplar();
        exemplar.setCodigo("test-001");
        exemplar.setStatus(StatusExemplar.DISPONIVEL);
        exemplar.setLivro(savedLivro);

        Exemplar savedExemplar = exemplarRepository.save(exemplar);

        assertThat(savedExemplar.getId()).isNotNull();
        assertThat(savedExemplar.getStatus()).isEqualTo(StatusExemplar.DISPONIVEL);
        assertThat(savedExemplar.getLivro().getId()).isEqualTo(savedLivro.getId());
    }
    
    @Test 
    void testFindExemplaresByLivroId() {
        Livro livro = new Livro();
        livro.setTitulo("titulo-test");
        livro.setAutor("autor-test");
        livro.setIsbn("isbn-test");

        Livro savedLivro = livroRepository.save(livro);

        Exemplar exemplar1 = new Exemplar();
        exemplar1.setCodigo("test-001");
        exemplar1.setStatus(StatusExemplar.DISPONIVEL);
        exemplar1.setLivro(savedLivro);

        Exemplar exemplar2 = new Exemplar();
        exemplar2.setCodigo("test-002");
        exemplar2.setStatus(StatusExemplar.EMPRESTADO);
        exemplar2.setLivro(savedLivro);

        exemplarRepository.save(exemplar1);
        exemplarRepository.save(exemplar2);

        List<Exemplar> exemplares = exemplarRepository.findByLivroId(savedLivro.getId());
        assertThat(exemplares).hasSize(2);
    }
}
