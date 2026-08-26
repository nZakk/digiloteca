package dev.isaac.digiloteca.repository;

import dev.isaac.digiloteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    
}
