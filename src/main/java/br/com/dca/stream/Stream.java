package br.com.dca.stream;

/**
 * Contrato define as funcionalidades para trabalhar com uma string.
 * 
 * 
 * @author Dênis Casado Aguilar
 */
public interface Stream {

    /**
     * @return o próximo caracter disponível no Stream.
     * 
     */
    char getNext();

    /**
     * @return boolean indicando se existe um próximo caracter disponível no Stream.
     */
    boolean hasNext();

}
