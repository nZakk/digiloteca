package dev.isaac.digiloteca.service;

import dev.isaac.digiloteca.dto.CriarReservaRequest;
import dev.isaac.digiloteca.dto.ReservaResponse;
import dev.isaac.digiloteca.enums.StatusExemplar;
import dev.isaac.digiloteca.enums.StatusReserva;
import dev.isaac.digiloteca.model.Exemplar;
import dev.isaac.digiloteca.model.Livro;
import dev.isaac.digiloteca.model.Reserva;
import dev.isaac.digiloteca.model.Usuario;
import dev.isaac.digiloteca.repository.ExemplarRepository;
import dev.isaac.digiloteca.repository.LivroRepository;
import dev.isaac.digiloteca.repository.ReservaRepository;
import dev.isaac.digiloteca.repository.UsuarioRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

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
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private ExemplarRepository exemplarRepository;

    @InjectMocks
    private ReservaService reservaService;


@Test
void mustCreateReservaAndChangeExemplarToReservado() {

    Usuario usuario = new Usuario();
    usuario.setId(1L);
    usuario.setNome("João");
    usuario.setAtivo(true);

    Livro livro = new Livro();
    livro.setId(2L);
    livro.setTitulo("1984");

    Exemplar exemplar = new Exemplar();
    exemplar.setId(10L);
    exemplar.setCodigo("1984-001");
    exemplar.setStatus(StatusExemplar.DISPONIVEL);
    exemplar.setLivro(livro);

    CriarReservaRequest request = new CriarReservaRequest();
    request.setUsuarioId(1L);
    request.setLivroId(2L);

    when(usuarioRepository.findById(1L))
            .thenReturn(Optional.of(usuario));

    when(livroRepository.findById(2L))
            .thenReturn(Optional.of(livro));

    when(exemplarRepository
            .findFirstByLivroIdAndStatusOrderByIdAsc(
                    2L,
                    StatusExemplar.DISPONIVEL
            ))
            .thenReturn(Optional.of(exemplar));

    when(exemplarRepository.save(exemplar))
            .thenReturn(exemplar);

    when(reservaRepository.save(any(Reserva.class)))
            .thenAnswer(invocation -> {

                Reserva reserva = invocation.getArgument(0);

                reserva.setId(100L);

                return reserva;
            });

    ReservaResponse resposta =
            reservaService.criar(request);

    assertThat(resposta.getId()).isEqualTo(100L);

    assertThat(resposta.getStatus())
            .isEqualTo(StatusReserva.ATIVA);

    assertThat(resposta.getUsuarioId()).isEqualTo(1L);
    assertThat(resposta.getLivroId()).isEqualTo(2L);
    assertThat(resposta.getExemplarId()).isEqualTo(10L);

    assertThat(exemplar.getStatus())
            .isEqualTo(StatusExemplar.RESERVADO);

    verify(exemplarRepository).save(exemplar);
    verify(reservaRepository).save(any(Reserva.class));
}

@Test
void cantCreateReservaWhenUsuarioDoesNotExist() {

    CriarReservaRequest request = new CriarReservaRequest();
    request.setUsuarioId(999L);
    request.setLivroId(2L);

    when(usuarioRepository.findById(999L))
            .thenReturn(Optional.empty());

    assertThatThrownBy(() ->
            reservaService.criar(request)
    )
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Usuário não encontrado");

    verify(reservaRepository, never()).save(any());
    verify(exemplarRepository, never()).save(any());
}

@Test
void cantCreateReservaWhenUsuarioIsInactive() {

    Usuario usuario = new Usuario();
    usuario.setId(1L);
    usuario.setAtivo(false);

    CriarReservaRequest request = new CriarReservaRequest();
    request.setUsuarioId(1L);
    request.setLivroId(2L);

    when(usuarioRepository.findById(1L))
            .thenReturn(Optional.of(usuario));

    assertThatThrownBy(() ->
            reservaService.criar(request)
    )
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining(
                    "Usuário inativo não pode realizar reservas"
            );

    verify(reservaRepository, never()).save(any());
}

@Test
void cantCreateReservaWhenLivroDoesNotExist() {

    Usuario usuario = new Usuario();
    usuario.setId(1L);
    usuario.setAtivo(true);

    CriarReservaRequest request = new CriarReservaRequest();
    request.setUsuarioId(1L);
    request.setLivroId(999L);

    when(usuarioRepository.findById(1L))
            .thenReturn(Optional.of(usuario));

    when(livroRepository.findById(999L))
            .thenReturn(Optional.empty());

    assertThatThrownBy(() ->
            reservaService.criar(request)
    )
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Livro não encontrado");

    verify(reservaRepository, never()).save(any());
    verify(exemplarRepository, never()).save(any());
}

@Test
void cantCreateReservaWhenNoExemplarIsAvailable() {

    Usuario usuario = new Usuario();
    usuario.setId(1L);
    usuario.setAtivo(true);

    Livro livro = new Livro();
    livro.setId(2L);
    livro.setTitulo("1984");

    CriarReservaRequest request = new CriarReservaRequest();
    request.setUsuarioId(1L);
    request.setLivroId(2L);

    when(usuarioRepository.findById(1L))
            .thenReturn(Optional.of(usuario));

    when(livroRepository.findById(2L))
            .thenReturn(Optional.of(livro));

    when(exemplarRepository
            .findFirstByLivroIdAndStatusOrderByIdAsc(
                    2L,
                    StatusExemplar.DISPONIVEL
            ))
            .thenReturn(Optional.empty());

    assertThatThrownBy(() ->
            reservaService.criar(request)
    )
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining(
                    "Não há exemplares disponíveis"
            );

    verify(reservaRepository, never()).save(any());
}
}