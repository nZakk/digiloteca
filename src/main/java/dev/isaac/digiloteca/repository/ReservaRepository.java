package dev.isaac.digiloteca.repository;

import dev.isaac.digiloteca.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository
        extends JpaRepository<Reserva, Long> {
}