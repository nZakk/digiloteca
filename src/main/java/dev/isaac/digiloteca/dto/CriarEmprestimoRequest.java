package dev.isaac.digiloteca.dto;

import jakarta.validation.constraints.NotNull;

public class CriarEmprestimoRequest {

    @NotNull(message = "A reserva é obrigatória.")
    private Long reservaId;

    public CriarEmprestimoRequest() {
    }

    public Long getReservaId() {
        return reservaId;
    }

    public void setReservaId(Long reservaId) {
        this.reservaId = reservaId;
    }
}