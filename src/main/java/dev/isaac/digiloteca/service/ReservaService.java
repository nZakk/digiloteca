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

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;
    private final ExemplarRepository exemplarRepository;

    public ReservaService(
            ReservaRepository reservaRepository,
            UsuarioRepository usuarioRepository,
            LivroRepository livroRepository,
            ExemplarRepository exemplarRepository) {

        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.livroRepository = livroRepository;
        this.exemplarRepository = exemplarRepository;
    }

    @Transactional
    public ReservaResponse criar(CriarReservaRequest request) {

        Usuario usuario = usuarioRepository
                .findById(request.getUsuarioId())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Usuário não encontrado."
                        )
                );

        if (!usuario.getAtivo()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Usuário inativo não pode realizar reservas."
            );
        }

        Livro livro = livroRepository
                .findById(request.getLivroId())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Livro não encontrado."
                        )
                );

        Exemplar exemplar = exemplarRepository
                .findFirstByLivroIdAndStatusOrderByIdAsc(
                        livro.getId(),
                        StatusExemplar.DISPONIVEL
                )
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.CONFLICT,
                                "Não há exemplares disponíveis para reserva."
                        )
                );

        exemplar.setStatus(StatusExemplar.RESERVADO);

        exemplarRepository.save(exemplar);

        Reserva reserva = new Reserva();

        reserva.setUsuario(usuario);
        reserva.setLivro(livro);
        reserva.setExemplar(exemplar);
        reserva.setDataReserva(LocalDateTime.now());
        reserva.setStatus(StatusReserva.ATIVA);

        Reserva reservaSalva =
                reservaRepository.save(reserva);

        return converterParaResponse(reservaSalva);
    }

    public List<ReservaResponse> listarTodas() {

        return reservaRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public ReservaResponse buscarPorId(Long id) {

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Reserva não encontrada."
                        )
                );

        return converterParaResponse(reserva);
    }

    private ReservaResponse converterParaResponse(
            Reserva reserva) {

        return new ReservaResponse(
                reserva.getId(),
                reserva.getStatus(),
                reserva.getDataReserva(),

                reserva.getUsuario().getId(),
                reserva.getUsuario().getNome(),

                reserva.getLivro().getId(),
                reserva.getLivro().getTitulo(),

                reserva.getExemplar().getId(),
                reserva.getExemplar().getCodigo()
        );
    }
}