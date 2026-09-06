package dev.isaac.digiloteca.dto;

import dev.isaac.digiloteca.enums.StatusExemplar;

public class ExemplarResumoResponse {

    private Long id;
    private String codigo;
    private StatusExemplar status;
    
    public ExemplarResumoResponse(Long id, String codigo, StatusExemplar status) {
        this.id = id;
        this.codigo = codigo;
        this.status = status;
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

}
