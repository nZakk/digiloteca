package dev.isaac.digiloteca.service;

import dev.isaac.digiloteca.dto.CriarEmprestimoRequest;
import dev.isaac.digiloteca.dto.EmprestimoResponse;
import dev.isaac.digiloteca.enums.StatusEmprestimo;
import dev.isaac.digiloteca.enums.StatusExemplar;
import dev.isaac.digiloteca.enums.StatusReserva;
import dev.isaac.digiloteca.model.Emprestimo;
import dev.isaac.digiloteca.model.Exemplar;
import dev.isaac.digiloteca.model.Reserva;
import dev.isaac.digiloteca.repository.EmprestimoRepository;
import dev.isaac.digiloteca.repository.ExemplarRepository;
import dev.isaac.digiloteca.repository.ReservaRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final ReservaRepository reservaRepository;
    private final ExemplarRepository exemplarRepository;

    public EmprestimoService(
            EmprestimoRepository emprestimoRepository,
            ReservaRepository reservaRepository,
            ExemplarRepository exemplarRepository) {

        this.emprestimoRepository = emprestimoRepository;
        this.reservaRepository = reservaRepository;
        this.exemplarRepository = exemplarRepository;
    }

    @Transactional
    public EmprestimoResponse criar(
            CriarEmprestimoRequest request) {

        Reserva reserva = reservaRepository
                .findById(request.getReservaId())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Reserva não encontrada."
                        )
                );

        if (reserva.getStatus() != StatusReserva.ATIVA) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A reserva não está ativa."
            );
        }

        if (emprestimoRepository
                .existsByReservaId(reserva.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esta reserva já possui um empréstimo."
            );
        }

        Exemplar exemplar = reserva.getExemplar();

        if (exemplar.getStatus() != StatusExemplar.RESERVADO) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "O exemplar da reserva não está reservado."
            );
        }

        LocalDateTime agora = LocalDateTime.now();

        Emprestimo emprestimo = new Emprestimo();

        emprestimo.setReserva(reserva);
        emprestimo.setUsuario(reserva.getUsuario());
        emprestimo.setExemplar(exemplar);

        emprestimo.setDataEmprestimo(agora);
        emprestimo.setDataPrevistaDevolucao(
                agora.plusDays(14)
        );

        emprestimo.setStatus(StatusEmprestimo.ATIVO);

        exemplar.setStatus(StatusExemplar.EMPRESTADO);
        reserva.setStatus(StatusReserva.FINALIZADA);

        exemplarRepository.save(exemplar);
        reservaRepository.save(reserva);

        Emprestimo emprestimoSalvo =
                emprestimoRepository.save(emprestimo);

        return converterParaResponse(emprestimoSalvo);
    }

        @Transactional
    public EmprestimoResponse devolver(Long id) {

        Emprestimo emprestimo = emprestimoRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Empréstimo não encontrado."
                        )
                );

        if (emprestimo.getStatus() == StatusEmprestimo.DEVOLVIDO) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Este empréstimo já foi devolvido."
            );
        }

        Exemplar exemplar = emprestimo.getExemplar();

        emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);
        emprestimo.setDataDevolucao(LocalDateTime.now());

        exemplar.setStatus(StatusExemplar.DISPONIVEL);

        exemplarRepository.save(exemplar);

        Emprestimo emprestimoSalvo =
                emprestimoRepository.save(emprestimo);

        return converterParaResponse(emprestimoSalvo);
    }

        public List<EmprestimoResponse> listarTodos() {

        return emprestimoRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public EmprestimoResponse buscarPorId(Long id) {

        Emprestimo emprestimo = emprestimoRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Empréstimo não encontrado."
                        )
                );

        return converterParaResponse(emprestimo);
    }

        private EmprestimoResponse converterParaResponse(
            Emprestimo emprestimo) {

        return new EmprestimoResponse(
                emprestimo.getId(),
                emprestimo.getStatus(),
                emprestimo.getDataEmprestimo(),
                emprestimo.getDataPrevistaDevolucao(),
                emprestimo.getDataDevolucao(),

                emprestimo.getReserva().getId(),

                emprestimo.getUsuario().getId(),
                emprestimo.getUsuario().getNome(),

                emprestimo.getExemplar().getId(),
                emprestimo.getExemplar().getCodigo(),

                emprestimo.getExemplar()
                        .getLivro()
                        .getTitulo()
        );
    }

}