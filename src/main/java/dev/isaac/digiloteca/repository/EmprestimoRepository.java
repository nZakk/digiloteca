package dev.isaac.digiloteca.repository;

import dev.isaac.digiloteca.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository
        extends JpaRepository<Emprestimo, Long> {

    boolean existsByReservaId(Long reservaId);
}