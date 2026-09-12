package dev.isaac.digiloteca.dto;

import dev.isaac.digiloteca.enums.StatusDoacao;

import java.time.LocalDateTime;

public class DoacaoResponse {

    private Long id;

    private Long usuarioId;
    private String nomeUsuario;

    private String tituloLivro;
    private String autor;
    private String isbn;
    private Integer quantidade;
    private String detalhes;
    private String categorias;

    private LocalDateTime dataSolicitacao;
    private StatusDoacao status;

    public DoacaoResponse(
            Long id,
            Long usuarioId,
            String nomeUsuario,
            String tituloLivro,
            String autor,
            String isbn,
            Integer quantidade,
            String detalhes,
            String categorias,
            LocalDateTime dataSolicitacao,
            StatusDoacao status) {

        this.id = id;
        this.usuarioId = usuarioId;
        this.nomeUsuario = nomeUsuario;
        this.tituloLivro = tituloLivro;
        this.autor = autor;
        this.isbn = isbn;
        this.quantidade = quantidade;
        this.detalhes = detalhes;
        this.categorias = categorias;
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getTituloLivro() {
        return tituloLivro;
    }

    public String getAutor() {
        return autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public String getCategorias() {
        return categorias;
    }

    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public StatusDoacao getStatus() {
        return status;
    }
}