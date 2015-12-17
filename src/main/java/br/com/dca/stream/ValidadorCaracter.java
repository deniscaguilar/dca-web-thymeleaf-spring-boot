package br.com.dca.stream;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * Classe responsável por implementar as regras para a interface Stream.
 * 
 * A partir de uma String informada, é mantido uma estrutura com 0 ou n caracteres que não se
 * repetem na String informada originalmente.
 * 
 * Caso exista mais de um caracter que não se repetem, eles serão organizados respeitando a ordem da
 * String informada originalmente.
 * 
 * Exemplo: String Informada: Cadeira -- Resultado Esperado: C, d, e, i, r
 * 
 * @author Dênis Casado Aguilar
 */
public class ValidadorCaracter implements Stream {

    private Iterator<Character> itCaracterSemRepeticao;

    public ValidadorCaracter(String texto) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(texto),
                        "Informe o texto para verificação");

        Map<Character, Integer> qtdeCaracteres = new LinkedHashMap<Character, Integer>();
        for (char caracter : texto.toCharArray()) {
            int qtd = qtdeCaracteres.containsKey(caracter) ? qtdeCaracteres.get(caracter) + 1 : 1;
            qtdeCaracteres.put(caracter, Integer.valueOf(qtd));
        }

        itCaracterSemRepeticao = qtdeCaracteres.keySet().stream()
                        .filter(c -> qtdeCaracteres.get(c) == 1).iterator();
    }

    @Override
    public char getNext() {
        return itCaracterSemRepeticao.next();
    }

    @Override
    public boolean hasNext() {
        return itCaracterSemRepeticao.hasNext();
    }

}
