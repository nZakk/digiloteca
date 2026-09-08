package dev.isaac.digiloteca.service;

import dev.isaac.digiloteca.dto.CriarExemplarRequest;
import dev.isaac.digiloteca.dto.ExemplarResponse;
import dev.isaac.digiloteca.enums.StatusExemplar;
import dev.isaac.digiloteca.dto.AtualizarStatusExemplarRequest;
import dev.isaac.digiloteca.model.Exemplar;
import dev.isaac.digiloteca.model.Livro;
import dev.isaac.digiloteca.repository.ExemplarRepository;
import dev.isaac.digiloteca.repository.LivroRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExemplarServiceTest {

    @Mock
    private ExemplarRepository exemplarRepository;

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private ExemplarService exemplarService;


    @Test
    void cantCreateExemplarForNonExistingLivro() {
        CriarExemplarRequest request = new CriarExemplarRequest();

        request.setCodigo("test-001");

        when(livroRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> exemplarService.criar(999L, request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Livro não encontrado");

        verify(exemplarRepository, never()).save(any());  
    }

    @Test
    void cantCreateExemplarWithDuplicateCodigo() {
        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("test");

        CriarExemplarRequest request = new CriarExemplarRequest();
        request.setCodigo("test-001");

        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(exemplarRepository.existsByCodigo("test-001")).thenReturn(true);

        assertThatThrownBy(() -> exemplarService.criar(1L, request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Exemplar com código já existe");

        verify(exemplarRepository, never()).save(any());
    }

    @Test
    void newExemplarIsDisponivel() {
        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("test");

        CriarExemplarRequest request = new CriarExemplarRequest();
        request.setCodigo("test-001");

        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(exemplarRepository.existsByCodigo("test-001")).thenReturn(false);
        when(exemplarRepository.save(any(Exemplar.class)))
            .thenAnswer(invocation -> {
                Exemplar exemplar = invocation.getArgument(0);
                exemplar.setId(10L);
                return exemplar;
            });

        ExemplarResponse response = exemplarService.criar(1L, request);

        ArgumentCaptor<Exemplar> captor =
        ArgumentCaptor.forClass(Exemplar.class);

        verify(exemplarRepository).save(captor.capture());

        Exemplar exemplarSalvo = captor.getValue();

        assertThat(exemplarSalvo.getStatus())
                .isEqualTo(StatusExemplar.DISPONIVEL);

        assertThat(exemplarSalvo.getLivro())
                .isEqualTo(livro);

        assertThat(response.getStatus()).isEqualTo(StatusExemplar.DISPONIVEL);
        assertThat(response.getCodigo()).isEqualTo("test-001");
        assertThat(response.getLivroId()).isEqualTo(1L);
        
    }

    @Test 
    void testUpdateExemplarStatus() {
        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("test");

        Exemplar exemplar = new Exemplar();
        exemplar.setId(10L);
        exemplar.setCodigo("test-001");
        exemplar.setStatus(StatusExemplar.DISPONIVEL);
        exemplar.setLivro(livro);

        AtualizarStatusExemplarRequest request = new AtualizarStatusExemplarRequest();
        request.setStatus(StatusExemplar.INDISPONIVEL);

        
        when(exemplarRepository.findById(10L))
                .thenReturn(Optional.of(exemplar));

        when(exemplarRepository.save(exemplar))
                .thenReturn(exemplar);

        ExemplarResponse resposta =
                exemplarService.atualizarStatus(10L, request);

        assertThat(resposta.getStatus())
                .isEqualTo(StatusExemplar.INDISPONIVEL);

        verify(exemplarRepository).save(exemplar);
    }

    @Test 
    void cantUpdateStatusOfNonExistingExemplar() {
        AtualizarStatusExemplarRequest request = new AtualizarStatusExemplarRequest();
        request.setStatus(StatusExemplar.INDISPONIVEL);

        when(exemplarRepository.findById(999L))
                .thenReturn(Optional.empty());
        
        assertThatThrownBy(() -> exemplarService.atualizarStatus(999L, request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Exemplar não encontrado");

        verify(exemplarRepository, never()).save(any());
    }
}

