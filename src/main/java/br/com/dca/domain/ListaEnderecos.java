package br.com.dca.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por manter a lista de endereços que será convertido em Json para trafegar os
 * dados.
 * 
 * 
 * @author Dênis Casado Aguilar
 */
public class ListaEnderecos {

    private final List<Endereco> enderecos = new ArrayList<>();
    
    public ListaEnderecos(List<Endereco> enderecos) {
        this.enderecos.addAll(enderecos);
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

}
