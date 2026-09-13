package dev.isaac.digiloteca.service;

import dev.isaac.digiloteca.dto.CriarEventoRequest;
import dev.isaac.digiloteca.dto.EventoResponse;
import dev.isaac.digiloteca.model.Evento;
import dev.isaac.digiloteca.repository.EventoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public EventoResponse criar(CriarEventoRequest request) {

        Evento evento = new Evento();

        evento.setTitulo(request.getTitulo());
        evento.setDescricao(request.getDescricao());
        evento.setDataHora(request.getDataHora());
        evento.setLocal(request.getLocal());

        evento.setDataCriacao(LocalDateTime.now());

        Evento eventoSalvo =
                eventoRepository.save(evento);

        return converterParaResponse(eventoSalvo);
    }

    public List<EventoResponse> listarTodos() {

        return eventoRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public EventoResponse buscarPorId(Long id) {

        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Evento não encontrado."
                        )
                );

        return converterParaResponse(evento);
    }

    public List<EventoResponse> listarProximos() {

        return eventoRepository
                .findByDataHoraAfterOrderByDataHoraAsc(
                        LocalDateTime.now()
                )
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    private EventoResponse converterParaResponse(
            Evento evento) {

        return new EventoResponse(
                evento.getId(),
                evento.getTitulo(),
                evento.getDescricao(),
                evento.getDataHora(),
                evento.getLocal(),
                evento.getDataCriacao()
        );
    }
}