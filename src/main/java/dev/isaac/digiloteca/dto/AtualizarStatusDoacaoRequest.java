package dev.isaac.digiloteca.dto;

import dev.isaac.digiloteca.enums.StatusDoacao;
import jakarta.validation.constraints.NotNull;

public class AtualizarStatusDoacaoRequest {

    @NotNull(message = "O status é obrigatório.")
    private StatusDoacao status;

    public AtualizarStatusDoacaoRequest() {
    }

    public StatusDoacao getStatus() {
        return status;
    }

    public void setStatus(StatusDoacao status) {
        this.status = status;
    }
}