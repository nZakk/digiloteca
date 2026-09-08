package dev.isaac.digiloteca.service;

import dev.isaac.digiloteca.model.Livro;
import dev.isaac.digiloteca.repository.ExemplarRepository;
import dev.isaac.digiloteca.repository.LivroRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LivroServiceTest {

    @Mock 
    private LivroRepository livroRepository;

    @Mock 
    private ExemplarRepository exemplarRepository;

    @InjectMocks
    private LivroService livroService;

    @Test 
    void testSaveNewLivro() {
        Livro livro = new Livro();
        livro.setTitulo("titulo-test");
        livro.setAutor("autor-test");
        livro.setIsbn("isbn-test");

        when(livroRepository.existsByIsbn("isbn-test")).thenReturn(false);
        when(livroRepository.save(livro)).thenReturn(livro);

        Livro savedLivro = livroService.salvar(livro);
    
        assertThat(savedLivro).isEqualTo(livro);
        verify(livroRepository).existsByIsbn("isbn-test");
        verify(livroRepository).save(livro);
    }

    @Test
    void testSaveLivroWithExistingIsbn() {
        Livro livro = new Livro();
        livro.setTitulo("titulo-test");
        livro.setAutor("autor-test");
        livro.setIsbn("isbn-test");

        when(livroRepository.existsByIsbn("isbn-test")).thenReturn(true);

        assertThatThrownBy(() -> livroService.salvar(livro))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Livro com ISBN já existe");

        verify(livroRepository).existsByIsbn("isbn-test");
        verify(livroRepository, never()).save(any());
    }
    
    @Test 
    void returnsConflictWhenIsbnAlreadyExists() {
        Livro livro = new Livro();
        livro.setIsbn("1234567890");

        when(livroRepository.existsByIsbn("1234567890")).thenReturn(true);

        try {
            livroService.salvar(livro);
        } catch (ResponseStatusException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        }

        verify(livroRepository,never()).save(any());
    }
}
