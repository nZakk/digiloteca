package dev.isaac.digiloteca.repository;

import dev.isaac.digiloteca.model.Livro;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@DataJpaTest
public class LivroRepositoryTest {

    @Autowired 
    private LivroRepository livroRepository;

    @Test 
    void testSaveLivro() {
        Livro livro = new Livro();
       
        livro.setTitulo("titulo-test");
        livro.setAutor("autor-test");
        livro.setIsbn("isbn-test");
        livro.setEditora("editora-test");
        livro.setAnoPublicacao(2023);
        livro.setCategorias("test");
        livro.setDescricao("descricao-test");

        Livro savedLivro = livroRepository.save(livro);

        assertThat(savedLivro.getId()).isNotNull();
        assertThat(savedLivro.getTitulo()).isEqualTo("titulo-test");
        assertThat(savedLivro.getAutor()).isEqualTo("autor-test");
    
    }

    @Test 
    void testFindLivroByIsbn(){
        Livro livro = new Livro();
       
        livro.setTitulo("titulo-test");
        livro.setAutor("autor-test");
        livro.setIsbn("isbn-test");
        livro.setEditora("editora-test");
        livro.setAnoPublicacao(2023);
        livro.setCategorias("test");
        livro.setDescricao("descricao-test");

        livroRepository.save(livro);

        var foundLivro = livroRepository.findByIsbn("isbn-test");

        assertThat(foundLivro).isPresent();
        assertThat(foundLivro.get().getTitulo()).isEqualTo("titulo-test");
    }

    @Test 
    void testFindLivroByIsbnNotFound(){
        var foundLivro = livroRepository.findByIsbn("isbn-not-found");

        assertThat(foundLivro).isNotPresent();
    }

    @Test 
    void testExistsByIsbn(){
        Livro livro = new Livro();
       
        livro.setTitulo("titulo-test");
        livro.setAutor("autor-test");
        livro.setIsbn("isbn-test");
        livro.setEditora("editora-test");
        livro.setAnoPublicacao(2023);
        livro.setCategorias("test");
        livro.setDescricao("descricao-test");

        livroRepository.save(livro);

        boolean exists = livroRepository.existsByIsbn("isbn-test");

        assertThat(exists).isTrue();
    }

    @Test 
    void testExistsByIsbnNotFound(){
        boolean exists = livroRepository.existsByIsbn("9999999999");

        assertThat(exists).isFalse();
    }

    
    
}
