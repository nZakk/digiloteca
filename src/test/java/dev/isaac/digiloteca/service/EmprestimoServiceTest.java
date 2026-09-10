package dev.isaac.digiloteca.service;

import dev.isaac.digiloteca.dto.CriarEmprestimoRequest;
import dev.isaac.digiloteca.dto.EmprestimoResponse;
import dev.isaac.digiloteca.enums.StatusEmprestimo;
import dev.isaac.digiloteca.enums.StatusExemplar;
import dev.isaac.digiloteca.enums.StatusReserva;
import dev.isaac.digiloteca.model.Emprestimo;
import dev.isaac.digiloteca.model.Exemplar;
import dev.isaac.digiloteca.model.Livro;
import dev.isaac.digiloteca.model.Reserva;
import dev.isaac.digiloteca.model.Usuario;
import dev.isaac.digiloteca.repository.EmprestimoRepository;
import dev.isaac.digiloteca.repository.ExemplarRepository;
import dev.isaac.digiloteca.repository.ReservaRepository;

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
class EmprestimoServiceTest {

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ExemplarRepository exemplarRepository;

    @InjectMocks
    private EmprestimoService emprestimoService;

    @Test
void createEmprestimoAndUpdatesStates() {

    Usuario usuario = new Usuario();
    usuario.setId(1L);
    usuario.setNome("João");

    Livro livro = new Livro();
    livro.setId(2L);
    livro.setTitulo("1984");

    Exemplar exemplar = new Exemplar();
    exemplar.setId(10L);
    exemplar.setCodigo("1984-001");
    exemplar.setStatus(StatusExemplar.RESERVADO);
    exemplar.setLivro(livro);

    Reserva reserva = new Reserva();
    reserva.setId(3L);
    reserva.setUsuario(usuario);
    reserva.setLivro(livro);
    reserva.setExemplar(exemplar);
    reserva.setStatus(StatusReserva.ATIVA);

    CriarEmprestimoRequest request =
            new CriarEmprestimoRequest();

    request.setReservaId(3L);

    when(reservaRepository.findById(3L))
            .thenReturn(Optional.of(reserva));

    when(emprestimoRepository.existsByReservaId(3L))
            .thenReturn(false);

    when(exemplarRepository.save(exemplar))
            .thenReturn(exemplar);

    when(reservaRepository.save(reserva))
            .thenReturn(reserva);

    when(emprestimoRepository.save(any(Emprestimo.class)))
            .thenAnswer(invocation -> {

                Emprestimo emprestimo =
                        invocation.getArgument(0);

                emprestimo.setId(100L);

                return emprestimo;
            });

    EmprestimoResponse resposta =
            emprestimoService.criar(request);

    assertThat(resposta.getId())
            .isEqualTo(100L);

    assertThat(resposta.getStatus())
            .isEqualTo(StatusEmprestimo.ATIVO);

    assertThat(exemplar.getStatus())
            .isEqualTo(StatusExemplar.EMPRESTADO);

    assertThat(reserva.getStatus())
            .isEqualTo(StatusReserva.FINALIZADA);

    assertThat(resposta.getDataEmprestimo())
            .isNotNull();

    assertThat(resposta.getDataPrevistaDevolucao())
            .isNotNull();

    assertThat(resposta.getDataDevolucao())
            .isNull();

    verify(emprestimoRepository)
            .save(any(Emprestimo.class));

    verify(exemplarRepository)
            .save(exemplar);

    verify(reservaRepository)
            .save(reserva);
}

@Test
void cantCreateEmprestimoWhenReservaDoesNotExist() {

    CriarEmprestimoRequest request =
            new CriarEmprestimoRequest();

    request.setReservaId(999L);

    when(reservaRepository.findById(999L))
            .thenReturn(Optional.empty());

    assertThatThrownBy(() ->
            emprestimoService.criar(request)
    )
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Reserva não encontrada");

    verify(emprestimoRepository, never())
            .save(any());
}

@Test
void cantCreateEmprestimoWhenReservaIsNotActive() {

    Reserva reserva = new Reserva();
    reserva.setId(3L);
    reserva.setStatus(StatusReserva.FINALIZADA);

    CriarEmprestimoRequest request =
            new CriarEmprestimoRequest();

    request.setReservaId(3L);

    when(reservaRepository.findById(3L))
            .thenReturn(Optional.of(reserva));

    assertThatThrownBy(() ->
            emprestimoService.criar(request)
    )
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("A reserva não está ativa");

    verify(emprestimoRepository, never())
            .save(any());
}

@Test
void cantCreateSecondEmprestimoForSameReserva() {

    Reserva reserva = new Reserva();
    reserva.setId(3L);
    reserva.setStatus(StatusReserva.ATIVA);

    CriarEmprestimoRequest request =
            new CriarEmprestimoRequest();

    request.setReservaId(3L);

    when(reservaRepository.findById(3L))
            .thenReturn(Optional.of(reserva));

    when(emprestimoRepository.existsByReservaId(3L))
            .thenReturn(true);

    assertThatThrownBy(() ->
            emprestimoService.criar(request)
    )
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining(
                    "Esta reserva já possui um empréstimo"
            );

    verify(emprestimoRepository, never())
            .save(any());
}

@Test
void cantCreateEmprestimoWhenExemplarIsNotReserved() {

    Livro livro = new Livro();
    livro.setId(2L);

    Exemplar exemplar = new Exemplar();
    exemplar.setId(10L);
    exemplar.setStatus(StatusExemplar.DISPONIVEL);
    exemplar.setLivro(livro);

    Reserva reserva = new Reserva();
    reserva.setId(3L);
    reserva.setStatus(StatusReserva.ATIVA);
    reserva.setExemplar(exemplar);

    CriarEmprestimoRequest request =
            new CriarEmprestimoRequest();

    request.setReservaId(3L);

    when(reservaRepository.findById(3L))
            .thenReturn(Optional.of(reserva));

    when(emprestimoRepository.existsByReservaId(3L))
            .thenReturn(false);

    assertThatThrownBy(() ->
            emprestimoService.criar(request)
    )
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining(
                    "O exemplar da reserva não está reservado"
            );

    verify(emprestimoRepository, never())
            .save(any());
}

@Test
void returnEmprestimoAndUpdateStates() {

    Livro livro = new Livro();
    livro.setId(2L);
    livro.setTitulo("1984");

    Exemplar exemplar = new Exemplar();
    exemplar.setId(10L);
    exemplar.setCodigo("1984-001");
    exemplar.setStatus(StatusExemplar.EMPRESTADO);
    exemplar.setLivro(livro);

    Usuario usuario = new Usuario();
    usuario.setId(1L);
    usuario.setNome("João");

    Reserva reserva = new Reserva();
    reserva.setId(3L);

    Emprestimo emprestimo = new Emprestimo();
    emprestimo.setId(100L);
    emprestimo.setStatus(StatusEmprestimo.ATIVO);
    emprestimo.setExemplar(exemplar);
    emprestimo.setUsuario(usuario);
    emprestimo.setReserva(reserva);

    when(emprestimoRepository.findById(100L))
            .thenReturn(Optional.of(emprestimo));

    when(exemplarRepository.save(exemplar))
            .thenReturn(exemplar);

    when(emprestimoRepository.save(emprestimo))
            .thenReturn(emprestimo);

    EmprestimoResponse resposta =
            emprestimoService.devolver(100L);

    assertThat(resposta.getStatus())
            .isEqualTo(StatusEmprestimo.DEVOLVIDO);

    assertThat(resposta.getDataDevolucao())
            .isNotNull();

    assertThat(exemplar.getStatus())
            .isEqualTo(StatusExemplar.DISPONIVEL);

    verify(exemplarRepository).save(exemplar);

    verify(emprestimoRepository)
            .save(emprestimo);
}

@Test
void cantReturnEmprestimoAlreadyReturned() {

    Emprestimo emprestimo = new Emprestimo();
    emprestimo.setId(100L);
    emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);

    when(emprestimoRepository.findById(100L))
            .thenReturn(Optional.of(emprestimo));

    assertThatThrownBy(() ->
            emprestimoService.devolver(100L)
    )
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining(
                    "Este empréstimo já foi devolvido"
            );

    verify(emprestimoRepository, never())
            .save(any());

    verify(exemplarRepository, never())
            .save(any());
}

@Test
void cantReturnEmprestimoWhenNotFound() {

    when(emprestimoRepository.findById(999L))
            .thenReturn(Optional.empty());

    assertThatThrownBy(() ->
            emprestimoService.devolver(999L)
    )
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Empréstimo não encontrado");

    verify(emprestimoRepository, never())
            .save(any());
}
}