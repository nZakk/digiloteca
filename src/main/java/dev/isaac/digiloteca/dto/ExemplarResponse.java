package dev.isaac.digiloteca.dto;

import dev.isaac.digiloteca.enums.StatusExemplar;

public class ExemplarResponse {
    
    private Long id;
    private String codigo;
    private StatusExemplar status; 
    private Long livroId;
    private String livroTitulo;

    public ExemplarResponse(
        Long id,
        String codigo, 
        StatusExemplar status,
        Long livroId,
        String livroTitulo) {

            this.id = id;
            this.codigo = codigo;
            this.status = status;
            this.livroId = livroId;
            this.livroTitulo = livroTitulo;
    }

    public Long getId() {
        return id;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public StatusExemplar getStatus() {
        return status;
    }

    public Long getLivroId() {
        return livroId;
    }

    public String getLivroTitulo() {
        return livroTitulo;
    }
}
