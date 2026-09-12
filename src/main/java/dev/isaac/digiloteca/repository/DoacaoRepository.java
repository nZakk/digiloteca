package dev.isaac.digiloteca.repository;

import dev.isaac.digiloteca.model.Doacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoacaoRepository
        extends JpaRepository<Doacao, Long> {
}