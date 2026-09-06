package dev.isaac.digiloteca.dto;

public class LivroResponse {

    private Long id;
    private String titulo;
    private String autor;
    private String editora;
    private String isbn;
    private Integer anoPublicacao;
    private String categorias;
    private String descricao;

    public LivroResponse(Long id,
        String titulo, 
        String autor, 
        String editora, 
        String isbn, 
        Integer anoPublicacao, 
        String categorias, 
        String descricao) {
        
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.isbn = isbn;
        this.anoPublicacao = anoPublicacao;
        this.categorias = categorias        ;
        this.descricao = descricao;
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
    
}
