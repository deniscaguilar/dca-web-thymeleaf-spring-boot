package br.com.dca.domain;

/**
 * Classe responsável por trafegar uma mensagem de status no formato Json.
 * 
 * 
 * @author Dênis Casado Aguilar
 *
 */
public class Mensagem {

    private final String status;

    public Mensagem(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
