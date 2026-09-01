package dev.isaac.digiloteca.dto;

import jakarta.validation.constraints.NotBlank;

public class CriarExemplarRequest {
    
    @NotBlank(message = "O código do exemplar é obrigatório")
    private String codigo;

    public CriarExemplarRequest() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
