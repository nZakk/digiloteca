package dev.isaac.digiloteca.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class ErroResponse {

    private LocalDateTime timestamp;
    private int status;
    private String erro;
    private String mensagem;
    private String caminho;
    private Map<String, String> campos;

    public ErroResponse(
            LocalDateTime timestamp,
            int status,
            String erro,
            String mensagem,
            String caminho,
            Map<String, String> campos) {

        this.timestamp = timestamp;
        this.status = status;
        this.erro = erro;
        this.mensagem = mensagem;
        this.caminho = caminho;
        this.campos = campos;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getErro() {
        return erro;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getCaminho() {
        return caminho;
    }

    public Map<String, String> getCampos() {
        return campos;
    }
}