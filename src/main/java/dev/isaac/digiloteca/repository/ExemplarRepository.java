package dev.isaac.digiloteca.repository;

import dev.isaac.digiloteca.model.Exemplar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExemplarRepository extends JpaRepository<Exemplar, Long> {
    boolean existsByCodigo(String codigo);
}
