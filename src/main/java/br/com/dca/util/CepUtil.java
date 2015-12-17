package br.com.dca.util;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Classe utilitária para realizar operações para auxiliar no trabalho com as manipulações dos ceps
 * 
 * @author Dênis Casado Aguilar
 *
 */
public class CepUtil {

    /**
     * Responsável por gerar uma lista de ceps que será utilizada na pesquisa, substituindo sempre o
     * último número da direita para esquerda por zero.
     * 
     * @param cep
     * @return lista de ceps gerados a partir do cep original informado.
     */
    public static Set<String> gerarSufixoParaCep(String cep) {
        Set<String> listaCeps = new LinkedHashSet<String>();
        listaCeps.add(cep);
        for (int i = cep.length() - 1; i > 0; i--) {
            String novoCep = String.format("%-8s", cep.substring(0, i)).replace(' ', '0');
            listaCeps.add(novoCep);
        }
        return listaCeps;
    }


}
