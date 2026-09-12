package dev.isaac.digiloteca.service;

import dev.isaac.digiloteca.dto.AtualizarStatusDoacaoRequest;
import dev.isaac.digiloteca.dto.CriarDoacaoRequest;
import dev.isaac.digiloteca.dto.DoacaoResponse;
import dev.isaac.digiloteca.enums.StatusDoacao;
import dev.isaac.digiloteca.model.Doacao;
import dev.isaac.digiloteca.model.Usuario;
import dev.isaac.digiloteca.repository.DoacaoRepository;
import dev.isaac.digiloteca.repository.UsuarioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DoacaoService {

    private final DoacaoRepository doacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public DoacaoService(
            DoacaoRepository doacaoRepository,
            UsuarioRepository usuarioRepository) {

        this.doacaoRepository = doacaoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public DoacaoResponse criar(
            CriarDoacaoRequest request) {

        Usuario usuario = usuarioRepository
                .findById(request.getUsuarioId())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Usuário não encontrado."
                        )
                );

        Doacao doacao = new Doacao();

        doacao.setUsuario(usuario);
        doacao.setTituloLivro(request.getTituloLivro());
        doacao.setAutor(request.getAutor());
        doacao.setIsbn(request.getIsbn());
        doacao.setQuantidade(request.getQuantidade());
        doacao.setDetalhes(request.getDetalhes());
        doacao.setCategorias(request.getCategorias());
        doacao.setDataSolicitacao(LocalDateTime.now());
        doacao.setStatus(StatusDoacao.PENDENTE);

        Doacao doacaoSalva =
                doacaoRepository.save(doacao);

        return converterParaResponse(doacaoSalva);
    }

    public List<DoacaoResponse> listarTodas() {

        return doacaoRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public DoacaoResponse buscarPorId(Long id) {

        Doacao doacao = doacaoRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Doação não encontrada."
                        )
                );

        return converterParaResponse(doacao);
    }

        public DoacaoResponse atualizarStatus(
            Long id,
            AtualizarStatusDoacaoRequest request) {

        Doacao doacao = doacaoRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Doação não encontrada."
                        )
                );

        validarTransicaoStatus(
                doacao.getStatus(),
                request.getStatus()
        );

        doacao.setStatus(request.getStatus());

        Doacao doacaoSalva =
                doacaoRepository.save(doacao);

        return converterParaResponse(doacaoSalva);
    }

        private void validarTransicaoStatus(
            StatusDoacao atual,
            StatusDoacao novo) {

        boolean transicaoValida =
                (atual == StatusDoacao.PENDENTE
                        && (novo == StatusDoacao.APROVADA
                        || novo == StatusDoacao.RECUSADA))

                ||

                (atual == StatusDoacao.APROVADA
                        && novo == StatusDoacao.RECEBIDA);

        if (!transicaoValida) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Transição de status da doação inválida."
            );
        }
    }
        private DoacaoResponse converterParaResponse(
            Doacao doacao) {

        return new DoacaoResponse(
                doacao.getId(),

                doacao.getUsuario().getId(),
                doacao.getUsuario().getNome(),

                doacao.getTituloLivro(),
                doacao.getAutor(),
                doacao.getIsbn(),
                doacao.getQuantidade(),
                doacao.getDetalhes(),
                doacao.getCategorias(),

                doacao.getDataSolicitacao(),
                doacao.getStatus()
        );
    }
}

