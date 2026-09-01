package dev.isaac.digiloteca;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import dev.isaac.digiloteca.enums.StatusExemplar;
import dev.isaac.digiloteca.model.Exemplar;
import dev.isaac.digiloteca.model.Livro;
import dev.isaac.digiloteca.repository.ExemplarRepository;
import dev.isaac.digiloteca.repository.LivroRepository;

@SpringBootApplication
public class DigilotecaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigilotecaApplication.class, args);
	}

	
}
