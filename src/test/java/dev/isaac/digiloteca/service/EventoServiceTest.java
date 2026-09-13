package dev.isaac.digiloteca.service;

import dev.isaac.digiloteca.dto.CriarEventoRequest;
import dev.isaac.digiloteca.dto.EventoResponse;
import dev.isaac.digiloteca.model.Evento;
import dev.isaac.digiloteca.repository.EventoRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventoServiceTest {

    @Mock
    private EventoRepository eventoRepository;

    @InjectMocks
    private EventoService eventoService;

    @Test
void createEvent() {

    CriarEventoRequest request =
            new CriarEventoRequest();

    LocalDateTime dataEvento =
            LocalDateTime.now().plusDays(10);

    request.setTitulo("Clube de Leitura");
    request.setDescricao(
            "Encontro para leitura e discussão de livros."
    );
    request.setDataHora(dataEvento);
    request.setLocal("Biblioteca Comunitária");

    when(eventoRepository.save(any(Evento.class)))
            .thenAnswer(invocation -> {

                Evento evento =
                        invocation.getArgument(0);

                evento.setId(1L);

                return evento;
            });

    EventoResponse resposta =
        eventoService.criar(request);

    ArgumentCaptor<Evento> captor =
        ArgumentCaptor.forClass(Evento.class);

    verify(eventoRepository)
        .save(captor.capture());

    Evento eventoSalvo = captor.getValue();

    assertThat(eventoSalvo.getTitulo())
        .isEqualTo("Clube de Leitura");

    assertThat(eventoSalvo.getDataHora())
        .isEqualTo(dataEvento);

    assertThat(eventoSalvo.getLocal())
        .isEqualTo("Biblioteca Comunitária");

    assertThat(eventoSalvo.getDataCriacao())
        .isNotNull();

    assertThat(resposta.getId())
            .isEqualTo(1L);

    assertThat(resposta.getTitulo())
            .isEqualTo("Clube de Leitura");

    assertThat(resposta.getDescricao())
            .isEqualTo(
                    "Encontro para leitura e discussão de livros."
            );

    assertThat(resposta.getDataHora())
            .isEqualTo(dataEvento);

    assertThat(resposta.getLocal())
            .isEqualTo("Biblioteca Comunitária");

    assertThat(resposta.getDataCriacao())
            .isNotNull();

    verify(eventoRepository)
            .save(any(Evento.class));
    }

    @Test
void searchEventById() {

    Evento evento = new Evento();

    evento.setId(1L);
    evento.setTitulo("Clube de Leitura");
    evento.setDescricao("Discussão comunitária.");
    evento.setDataHora(
            LocalDateTime.now().plusDays(5)
    );
    evento.setLocal("Biblioteca");
    evento.setDataCriacao(
            LocalDateTime.now()
    );

    when(eventoRepository.findById(1L))
            .thenReturn(Optional.of(evento));

    EventoResponse resposta =
            eventoService.buscarPorId(1L);

    assertThat(resposta.getId())
            .isEqualTo(1L);

    assertThat(resposta.getTitulo())
            .isEqualTo("Clube de Leitura");

    verify(eventoRepository)
            .findById(1L);
}

@Test
void cantFindInexistentEvent() {

    when(eventoRepository.findById(999L))
            .thenReturn(Optional.empty());

    assertThatThrownBy(() ->
            eventoService.buscarPorId(999L)
    )
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining(
                    "Evento não encontrado"
            );
}

@Test
void listAllEvents() {

    Evento evento1 = new Evento();

    evento1.setId(1L);
    evento1.setTitulo("Clube de Leitura");
    evento1.setDescricao("Evento 1");
    evento1.setDataHora(
            LocalDateTime.now().plusDays(5)
    );
    evento1.setLocal("Biblioteca");
    evento1.setDataCriacao(
            LocalDateTime.now()
    );

    Evento evento2 = new Evento();

    evento2.setId(2L);
    evento2.setTitulo("Oficina de Escrita");
    evento2.setDescricao("Evento 2");
    evento2.setDataHora(
            LocalDateTime.now().plusDays(10)
    );
    evento2.setLocal("Biblioteca");
    evento2.setDataCriacao(
            LocalDateTime.now()
    );

    when(eventoRepository.findAll())
            .thenReturn(
                    List.of(evento1, evento2)
            );

    List<EventoResponse> resposta =
            eventoService.listarTodos();

    assertThat(resposta)
            .hasSize(2);

    assertThat(resposta.get(0).getTitulo())
            .isEqualTo("Clube de Leitura");

    assertThat(resposta.get(1).getTitulo())
            .isEqualTo("Oficina de Escrita");
}

@Test
void listUpcomingEvents() {

    Evento evento1 = new Evento();

    evento1.setId(1L);
    evento1.setTitulo("Oficina de Escrita");
    evento1.setDescricao("Evento próximo");
    evento1.setDataHora(
            LocalDateTime.now().plusDays(5)
    );
    evento1.setLocal("Biblioteca");
    evento1.setDataCriacao(
            LocalDateTime.now()
    );

    Evento evento2 = new Evento();

    evento2.setId(2L);
    evento2.setTitulo("Clube de Leitura");
    evento2.setDescricao("Outro evento");
    evento2.setDataHora(
            LocalDateTime.now().plusDays(10)
    );
    evento2.setLocal("Biblioteca");
    evento2.setDataCriacao(
            LocalDateTime.now()
    );

    when(eventoRepository
            .findByDataHoraAfterOrderByDataHoraAsc(
                    any(LocalDateTime.class)
            ))
            .thenReturn(
                    List.of(evento1, evento2)
            );

    List<EventoResponse> resposta =
            eventoService.listarProximos();

    assertThat(resposta)
            .hasSize(2);

    assertThat(resposta.get(0).getTitulo())
            .isEqualTo("Oficina de Escrita");

    assertThat(resposta.get(1).getTitulo())
            .isEqualTo("Clube de Leitura");

    verify(eventoRepository)
            .findByDataHoraAfterOrderByDataHoraAsc(
                    any(LocalDateTime.class)
            );
}
}