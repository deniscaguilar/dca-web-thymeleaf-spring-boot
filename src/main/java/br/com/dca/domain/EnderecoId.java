package br.com.dca.domain;

/**
 * Classe responsável por trafegar o id do endereço no formato Json.
 * 
 * 
 * @author Dênis Casado Aguilar
 */
public class EnderecoId {

    private final Long id;

    public EnderecoId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
