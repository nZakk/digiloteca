package dev.isaac.digiloteca.dto;

import java.util.List;

import dev.isaac.digiloteca.enums.StatusExemplar;

public class LivroDetalheResponse {

    private Long id;
    private String titulo;
    private String autor;
    private String editora;
    private String isbn;
    private Integer anoPublicacao;
    private String categorias;
    private String descricao;
    private List<ExemplarResumoResponse> exemplares;
    private Integer quantidadeExemplares;
    private Integer quantidadeExemplaresDisponiveis;

    public LivroDetalheResponse(Long id, String titulo, String autor, String editora, String isbn, Integer anoPublicacao, String categorias, String descricao, List<ExemplarResumoResponse> exemplares) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.isbn = isbn;
        this.anoPublicacao = anoPublicacao;
        this.categorias = categorias;
        this.descricao = descricao;
        this.exemplares = exemplares;
        this.quantidadeExemplares = exemplares.size();
        this.quantidadeExemplaresDisponiveis = (int) exemplares.stream()
                .filter(exemplar -> exemplar.getStatus() == StatusExemplar.DISPONIVEL)
                .count();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getEditora() {
        return editora;
    }

    public String getIsbn() {
        return isbn;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public String getCategorias() {
        return categorias;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<ExemplarResumoResponse> getExemplares() {
        return exemplares;
    }

    public Integer getQuantidadeExemplares() {
        return quantidadeExemplares;
    }

    public Integer getQuantidadeExemplaresDisponiveis() {
        return quantidadeExemplaresDisponiveis;
    }
    
}
