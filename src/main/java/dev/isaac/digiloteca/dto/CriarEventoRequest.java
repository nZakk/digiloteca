package dev.isaac.digiloteca.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class CriarEventoRequest {

    @NotBlank(message = "O título é obrigatório.")
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória.")
    private String descricao;

    @NotNull(message = "A data e hora são obrigatórias.")
    @Future(message = "A data do evento deve estar no futuro.")
    private LocalDateTime dataHora;

    @NotBlank(message = "O local é obrigatório.")
    private String local;

    public CriarEventoRequest() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}