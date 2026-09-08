package dev.isaac.digiloteca.dto;

import dev.isaac.digiloteca.enums.StatusExemplar;
import jakarta.validation.constraints.NotNull;

public class AtualizarStatusExemplarRequest {
    
    @NotNull(message = "O status do exemplar é obrigatório.")
    private StatusExemplar status;

    public AtualizarStatusExemplarRequest() {
    }

    public StatusExemplar getStatus() {
        return status;
    }

    public void setStatus(StatusExemplar status) {
        this.status = status;
    }
}
