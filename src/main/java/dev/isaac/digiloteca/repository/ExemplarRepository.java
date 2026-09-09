package dev.isaac.digiloteca.repository;

import dev.isaac.digiloteca.enums.StatusExemplar;
import dev.isaac.digiloteca.model.Exemplar;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExemplarRepository extends JpaRepository<Exemplar, Long> {
    boolean existsByCodigo(String codigo);
    List<Exemplar> findByLivroId(Long livroId);

    Optional<Exemplar> findFirstByLivroIdAndStatusOrderByIdAsc(
        Long livroId,
        StatusExemplar status
);
}

