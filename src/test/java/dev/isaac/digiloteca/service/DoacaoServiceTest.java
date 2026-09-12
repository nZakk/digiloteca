package dev.isaac.digiloteca.service;

import dev.isaac.digiloteca.dto.AtualizarStatusDoacaoRequest;
import dev.isaac.digiloteca.dto.CriarDoacaoRequest;
import dev.isaac.digiloteca.dto.DoacaoResponse;
import dev.isaac.digiloteca.enums.StatusDoacao;
import dev.isaac.digiloteca.model.Doacao;
import dev.isaac.digiloteca.model.Usuario;
import dev.isaac.digiloteca.repository.DoacaoRepository;
import dev.isaac.digiloteca.repository.UsuarioRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.ArgumentCaptor;

import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoacaoServiceTest {

    @Mock
    private DoacaoRepository doacaoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private DoacaoService doacaoService;

    @Test
void CreateDoacaoPendente() {

    Usuario usuario = new Usuario();
    usuario.setId(1L);
    usuario.setNome("João");
    usuario.setAtivo(true);

    CriarDoacaoRequest request = new CriarDoacaoRequest();

    request.setUsuarioId(1L);
    request.setTituloLivro("O Pequeno Príncipe");
    request.setAutor("Antoine de Saint-Exupéry");
    request.setIsbn("9788595081512");
    request.setQuantidade(2);

    request.setCategorias("Literatura Infantil");
    request.setDetalhes("Dois exemplares em bom estado.");

    when(usuarioRepository.findById(1L))
            .thenReturn(Optional.of(usuario));

    when(doacaoRepository.save(any(Doacao.class)))
            .thenAnswer(invocation -> {

                Doacao doacao = invocation.getArgument(0);

                doacao.setId(10L);

                return doacao;
            });

    DoacaoResponse resposta =
            doacaoService.criar(request);

    assertThat(resposta.getId())
            .isEqualTo(10L);

    assertThat(resposta.getUsuarioId())
            .isEqualTo(1L);

    assertThat(resposta.getTituloLivro())
            .isEqualTo("O Pequeno Príncipe");

    assertThat(resposta.getQuantidade())
            .isEqualTo(2);

    assertThat(resposta.getCategorias())
            .isEqualTo("Literatura Infantil");

    assertThat(resposta.getDetalhes())
            .isEqualTo("Dois exemplares em bom estado.");

    assertThat(resposta.getStatus())
            .isEqualTo(StatusDoacao.PENDENTE);

    assertThat(resposta.getDataSolicitacao())
            .isNotNull();

    ArgumentCaptor<Doacao> captor =
        ArgumentCaptor.forClass(Doacao.class);

    verify(doacaoRepository)
            .save(captor.capture());

    Doacao doacaoSalva = captor.getValue();

    assertThat(doacaoSalva.getUsuario())
            .isEqualTo(usuario);

    assertThat(doacaoSalva.getStatus())
            .isEqualTo(StatusDoacao.PENDENTE);

    assertThat(doacaoSalva.getCategorias())
            .isEqualTo("Literatura Infantil");

    assertThat(doacaoSalva.getDetalhes())
            .isEqualTo("Dois exemplares em bom estado.");
}

@Test
void cantCreateDoacaoWhenUserDoesNotExist() {

    CriarDoacaoRequest request =
            new CriarDoacaoRequest();

    request.setUsuarioId(999L);
    request.setTituloLivro("Duna");
    request.setAutor("Frank Herbert");
    request.setQuantidade(1);
    request.setCategorias("Ficção Científica");
    request.setDetalhes("Exemplar usado.");

    when(usuarioRepository.findById(999L))
            .thenReturn(Optional.empty());

    assertThatThrownBy(() ->
            doacaoService.criar(request)
    )
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Usuário não encontrado");

    verify(doacaoRepository, never())
            .save(any());
}

@Test
void shouldApprovePendingDonation() {

    Doacao doacao = new Doacao();

    doacao.setId(10L);
    doacao.setStatus(StatusDoacao.PENDENTE);

    Usuario usuario = new Usuario();
    usuario.setId(1L);
    usuario.setNome("João");

    doacao.setUsuario(usuario);

    doacao.setTituloLivro("Duna");
    doacao.setAutor("Frank Herbert");
    doacao.setQuantidade(1);

    AtualizarStatusDoacaoRequest request =
            new AtualizarStatusDoacaoRequest();

    request.setStatus(StatusDoacao.APROVADA);

    when(doacaoRepository.findById(10L))
            .thenReturn(Optional.of(doacao));

    when(doacaoRepository.save(doacao))
            .thenReturn(doacao);

    DoacaoResponse resposta =
            doacaoService.atualizarStatus(10L, request);

    assertThat(resposta.getStatus())
            .isEqualTo(StatusDoacao.APROVADA);

    assertThat(doacao.getStatus())
            .isEqualTo(StatusDoacao.APROVADA);

    verify(doacaoRepository).save(doacao);
}

@Test
void shouldMarkApprovedDonationAsReceived() {

    Doacao doacao = new Doacao();

    doacao.setId(10L);
    doacao.setStatus(StatusDoacao.APROVADA);

    Usuario usuario = new Usuario();
    usuario.setId(1L);
    usuario.setNome("João");

    doacao.setUsuario(usuario);

    doacao.setTituloLivro("Duna");
    doacao.setAutor("Frank Herbert");
    doacao.setQuantidade(1);

    AtualizarStatusDoacaoRequest request =
            new AtualizarStatusDoacaoRequest();

    request.setStatus(StatusDoacao.RECEBIDA);

    when(doacaoRepository.findById(10L))
            .thenReturn(Optional.of(doacao));

    when(doacaoRepository.save(doacao))
            .thenReturn(doacao);

    DoacaoResponse resposta =
            doacaoService.atualizarStatus(10L, request);

    assertThat(resposta.getStatus())
            .isEqualTo(StatusDoacao.RECEBIDA);

    verify(doacaoRepository).save(doacao);
}

@Test
void shouldRejectPendingDonation() {

    Doacao doacao = new Doacao();

    doacao.setId(10L);
    doacao.setStatus(StatusDoacao.PENDENTE);

    Usuario usuario = new Usuario();
    usuario.setId(1L);
    usuario.setNome("João");

    doacao.setUsuario(usuario);

    doacao.setTituloLivro("Duna");
    doacao.setAutor("Frank Herbert");
    doacao.setQuantidade(1);

    AtualizarStatusDoacaoRequest request =
            new AtualizarStatusDoacaoRequest();

    request.setStatus(StatusDoacao.RECUSADA);

    when(doacaoRepository.findById(10L))
            .thenReturn(Optional.of(doacao));

    when(doacaoRepository.save(doacao))
            .thenReturn(doacao);

    DoacaoResponse resposta =
            doacaoService.atualizarStatus(10L, request);

    assertThat(resposta.getStatus())
            .isEqualTo(StatusDoacao.RECUSADA);
}

@Test
void shouldNotTransitionDirectlyFromPendingToReceived() {

    Doacao doacao = new Doacao();

    doacao.setId(10L);
    doacao.setStatus(StatusDoacao.PENDENTE);

    AtualizarStatusDoacaoRequest request =
            new AtualizarStatusDoacaoRequest();

    request.setStatus(StatusDoacao.RECEBIDA);

    when(doacaoRepository.findById(10L))
            .thenReturn(Optional.of(doacao));

    assertThatThrownBy(() ->
            doacaoService.atualizarStatus(10L, request)
    )
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining(
                    "Transição de status da doação inválida"
            );

    verify(doacaoRepository, never())
            .save(any());
}

@Test
void shouldNotAlterStatusOfReceivedDonation() {

    Doacao doacao = new Doacao();

    doacao.setId(10L);
    doacao.setStatus(StatusDoacao.RECEBIDA);

    AtualizarStatusDoacaoRequest request =
            new AtualizarStatusDoacaoRequest();

    request.setStatus(StatusDoacao.PENDENTE);

    when(doacaoRepository.findById(10L))
            .thenReturn(Optional.of(doacao));

    assertThatThrownBy(() ->
            doacaoService.atualizarStatus(10L, request)
    )
            .isInstanceOf(ResponseStatusException.class);

    verify(doacaoRepository, never())
            .save(any());
}

@Test
void shouldNotUpdateNonExistentDonation() {

    AtualizarStatusDoacaoRequest request =
            new AtualizarStatusDoacaoRequest();

    request.setStatus(StatusDoacao.APROVADA);

    when(doacaoRepository.findById(999L))
            .thenReturn(Optional.empty());

    assertThatThrownBy(() ->
            doacaoService.atualizarStatus(999L, request)
    )
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Doação não encontrada");

    verify(doacaoRepository, never())
            .save(any());
}
}