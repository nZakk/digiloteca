package dev.isaac.digiloteca.repository;

import dev.isaac.digiloteca.model.Evento;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventoRepository
        extends JpaRepository<Evento, Long> {

    List<Evento> findByDataHoraAfterOrderByDataHoraAsc(
            LocalDateTime dataHora
    );
}