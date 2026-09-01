package dev.isaac.digiloteca.model;

import jakarta.persistence.*;
import dev.isaac.digiloteca.model.enums.StatusExemplar;

@Entity
@Table(name = "exemplar")
public class Exemplar {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusExemplar status;

    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    public Exemplar() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public StatusExemplar getStatus() {
        return status;
    }

    public void setStatus(StatusExemplar status) {
        this.status = status;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

}
