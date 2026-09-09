package dev.isaac.digiloteca.dto;

import dev.isaac.digiloteca.enums.StatusReserva;

import java.time.LocalDateTime;

public class ReservaResponse {

    private Long id;
    private StatusReserva status;
    private LocalDateTime dataReserva;

    private Long usuarioId;
    private String nomeUsuario;

    private Long livroId;
    private String tituloLivro;

    private Long exemplarId;
    private String codigoExemplar;

    public ReservaResponse(
            Long id,
            StatusReserva status,
            LocalDateTime dataReserva,
            Long usuarioId,
            String nomeUsuario,
            Long livroId,
            String tituloLivro,
            Long exemplarId,
            String codigoExemplar) {

        this.id = id;
        this.status = status;
        this.dataReserva = dataReserva;
        this.usuarioId = usuarioId;
        this.nomeUsuario = nomeUsuario;
        this.livroId = livroId;
        this.tituloLivro = tituloLivro;
        this.exemplarId = exemplarId;
        this.codigoExemplar = codigoExemplar;
    }

    public Long getId() {
        return id;
    }

    public StatusReserva getStatus() {
        return status;
    }

    public LocalDateTime getDataReserva() {
        return dataReserva;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public Long getLivroId() {
        return livroId;
    }

    public String getTituloLivro() {
        return tituloLivro;
    }

    public Long getExemplarId() {
        return exemplarId;
    }

    public String getCodigoExemplar() {
        return codigoExemplar;
    }
}