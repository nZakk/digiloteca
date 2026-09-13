package dev.isaac.digiloteca.dto;

import java.time.LocalDateTime;

public class EventoResponse {

    private Long id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataHora;
    private String local;
    private LocalDateTime dataCriacao;

    public EventoResponse(
            Long id,
            String titulo,
            String descricao,
            LocalDateTime dataHora,
            String local,
            LocalDateTime dataCriacao) {

        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataHora = dataHora;
        this.local = local;
        this.dataCriacao = dataCriacao;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getLocal() {
        return local;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
}