package dev.isaac.digiloteca.dto;

import dev.isaac.digiloteca.enums.StatusEmprestimo;

import java.time.LocalDateTime;

public class EmprestimoResponse {

    private Long id;
    private StatusEmprestimo status;

    private LocalDateTime dataEmprestimo;
    private LocalDateTime dataPrevistaDevolucao;
    private LocalDateTime dataDevolucao;

    private Long reservaId;

    private Long usuarioId;
    private String nomeUsuario;

    private Long exemplarId;
    private String codigoExemplar;

    private String tituloLivro;

    public EmprestimoResponse(
            Long id,
            StatusEmprestimo status,
            LocalDateTime dataEmprestimo,
            LocalDateTime dataPrevistaDevolucao,
            LocalDateTime dataDevolucao,
            Long reservaId,
            Long usuarioId,
            String nomeUsuario,
            Long exemplarId,
            String codigoExemplar,
            String tituloLivro) {

        this.id = id;
        this.status = status;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
        this.dataDevolucao = dataDevolucao;
        this.reservaId = reservaId;
        this.usuarioId = usuarioId;
        this.nomeUsuario = nomeUsuario;
        this.exemplarId = exemplarId;
        this.codigoExemplar = codigoExemplar;
        this.tituloLivro = tituloLivro;
    }

    public Long getId() {
        return id;
    }

    public StatusEmprestimo getStatus() {
        return status;
    }

    public LocalDateTime getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDateTime getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public LocalDateTime getDataDevolucao() {
        return dataDevolucao;
    }

    public Long getReservaId() {
        return reservaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public Long getExemplarId() {
        return exemplarId;
    }

    public String getCodigoExemplar() {
        return codigoExemplar;
    }

    public String getTituloLivro() {
        return tituloLivro;
    }
}