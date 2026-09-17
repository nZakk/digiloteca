package dev.isaac.digiloteca.service;

import dev.isaac.digiloteca.dto.LoginRequest;
import dev.isaac.digiloteca.dto.UsuarioResponse;
import dev.isaac.digiloteca.model.Usuario;
import dev.isaac.digiloteca.repository.UsuarioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "Email ou senha inválidos."
                        )
                );

        if (!passwordEncoder.matches(
                request.getSenha(),
                usuario.getSenha())) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Email ou senha inválidos."
            );
        }

        if (!usuario.getAtivo()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Usuário inativo."
            );
        }

        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getDataCadastro(),
                usuario.getAtivo()
        );
    }
}