package dev.isaac.digiloteca.service;

import dev.isaac.digiloteca.dto.CriarUsuarioRequest;
import dev.isaac.digiloteca.dto.UsuarioResponse;
import dev.isaac.digiloteca.model.Usuario;
import dev.isaac.digiloteca.repository.UsuarioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponse criar(CriarUsuarioRequest request) {

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe um usuário cadastrado com este email."
            );
        }

        Usuario usuario = new Usuario();

        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setTelefone(request.getTelefone());

        usuario.setDataCadastro(LocalDateTime.now());
        usuario.setAtivo(true);

        Usuario usuarioSalvo =
                usuarioRepository.save(usuario);

        return converterParaResponse(usuarioSalvo);
    }

    public List<UsuarioResponse> listarTodos() {

        return usuarioRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public UsuarioResponse buscarPorId(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Usuário não encontrado."
                        )
                );

        return converterParaResponse(usuario);
    }

    private UsuarioResponse converterParaResponse(
            Usuario usuario) {

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