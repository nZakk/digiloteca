package dev.isaac.digiloteca.dto;

import jakarta.validation.constraints.NotNull;

public class CriarReservaRequest {

    @NotNull(message = "O usuário é obrigatório.")
    private Long usuarioId;

    @NotNull(message = "O livro é obrigatório.")
    private Long livroId;

    public CriarReservaRequest() {
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getLivroId() {
        return livroId;
    }

    public void setLivroId(Long livroId) {
        this.livroId = livroId;
    }
}