package br.com.dca.stream.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

import br.com.dca.stream.Stream;
import br.com.dca.stream.ValidadorCaracter;

/**
 * 
 * Responsável pelos testes do componente ValidadorCaracter.
 * 
 * 
 * @author Dênis Casado Aguilar
 */
public class ValidadorCaracterTest {

    /**
     * Passa um String vazia para o construtor de ValidadorCaracter. O resultado esperado é que seja
     * lançada uma exceção do tipo IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void validarStreamSemConteudo() {
        Stream st = new ValidadorCaracter("");
        st.hasNext();
    }

    /**
     * Cria instâncias de ValidadorCaracter, todos com String que não possuem caracteres sem se
     * repetir e aciona o método hasNext(). O resultado esperado é que todas as instâncias criadas
     * retornem false.
     */
    @Test
    public void validarHasNextStreamVazio() {
        Stream st = new ValidadorCaracter("AAA");
        assertFalse(st.hasNext());

        st = new ValidadorCaracter("ABCABC");
        assertFalse(st.hasNext());
    }

    /**
     * Cria um instância de ValidadorCaracter com uma String que não possue caracter que se repete e
     * aciona o método getNext(). O resultado esperado é que seja lançado um exceção do tipo
     * NoSuchElementException.
     */
    @Test(expected = NoSuchElementException.class)
    public void validarGetNextStreamVazio() {
        Stream st = new ValidadorCaracter("WZZW");
        st.getNext();
    }

    /**
     * Cria instâncias de ValidadorCaracter, passando Strings que possuem caracter sem repetir.
     * Realiza chamadas ao método hasNext() e o resultado esperado deve ser true e aciona o método
     * getNext(), analisando o conteúdo com o caracteres previstos.
     */
    @Test
    public void validarSomentePrimeiroStreamDoCaracter() {
        Stream st = new ValidadorCaracter("Java");
        assertTrue(st.hasNext());
        assertSame('J', st.getNext());

        st = new ValidadorCaracter("programar por diversão");
        assertTrue(st.hasNext());
        assertSame('g', st.getNext());

        st = new ValidadorCaracter("pneumoultramicroscopicossilicovulcaniotico");
        assertTrue(st.hasNext());
        char c = st.getNext();
        assertNotEquals('p', c);
        assertSame('e', c);
    }

    /**
     * Cria uma instância de ValidadorCaracter, com uma String que possue apenas um caracter que não
     * se repete, faz a chamada ao método getNext duas vezes. Na primeira vez o resultado esperado é
     * que o método retorne o caracter que não se repete. A segunda vez que foi chamado é esperado
     * que seja lançado uma exceção do tipo NoSuchElementException.
     */
    @Test(expected = NoSuchElementException.class)
    public void validarSomentePrimeiroInvalido() {
        Stream st = new ValidadorCaracter("ssempprrre");
        assertTrue(st.hasNext());
        assertSame('m', st.getNext());
        assertNotEquals('e', st.getNext());
    }

    /**
     * Cria instâncoas de ValidadorCaracter, com Strings que possuem vários caracteres que não se
     * repetem. Faz a chamada para o método getNext(), múltiplas vezes. O resultado esperando é que
     * os caracteres previstos sejam analisados em cada chamada.
     */
    @Test
    public void validarTodoStreamCaracteres() {
        Stream st = new ValidadorCaracter("escrevendo um conteudo maior para testar o stream");
        List<Character> actuals = new ArrayList<Character>();
        while (st.hasNext()) {
            actuals.add(st.getNext());
        }
        Character[] expecteds = new Character[] {'v', 'i', 'p'};
        assertArrayEquals(expecteds, actuals.toArray(new Character[] {}));

        st = new ValidadorCaracter("testando novamente sem perguntar se tem caracter disponível");
        assertSame('g', st.getNext());
        assertSame('u', st.getNext());
        assertSame('i', st.getNext());
        assertTrue("í".equals("" + st.getNext()));
        assertSame('l', st.getNext());
        assertFalse(st.hasNext());
    }

    /**
     * Cria instâncias de ValidadorCaracter com String formadas por caracteres especiais que não se
     * repetem. Faz a chamada ao método getNext(), múltiplas vezes. O resultado esperado é que os
     * caracteres sejam analisados
     */
    @Test
    public void validarStreamCaracteresEspeciais() {
        Stream st = new ValidadorCaracter("aAbBABac");
        List<Character> actuals = new ArrayList<Character>();
        while (st.hasNext()) {
            actuals.add(st.getNext());
        }
        Character[] expecteds = new Character[] {'b', 'c'};
        assertArrayEquals(expecteds, actuals.toArray(new Character[] {}));

        st = new ValidadorCaracter("123Aa -25,0;Eó");
        actuals = new ArrayList<>();
        while (st.hasNext()) {
            actuals.add(st.getNext());
        }
        expecteds = new Character[] {'1', '3', 'A', 'a', ' ', '-', '5', ',', '0', ';', 'E', 'ó'};
        assertArrayEquals(expecteds, actuals.toArray(new Character[] {}));
    }

}
