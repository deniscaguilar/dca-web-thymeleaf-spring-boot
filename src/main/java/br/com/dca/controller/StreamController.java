package br.com.dca.controller;

import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.dca.stream.Stream;
import br.com.dca.stream.ValidadorCaracter;

/**
 * Controller Rest responsável por expor os serviços para manipulação de Strings.
 * 
 * 
 * @author Dênis Casado Aguilar
 */
@RestController
@RequestMapping(value = "/stream")
public class StreamController {

    private static final Logger log = LoggerFactory.getLogger(StreamController.class);

    /**
     * Recebe um texto (String) para testar a stream, retorna uma string com os caracteres não
     * repetidos.
     * 
     * @param conteudo
     */
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody String validarStreamCaracter(@RequestParam String texto) {
        Stream stream = new ValidadorCaracter(texto);
        StringJoiner caracteresNaoRepetidos = new StringJoiner(", ");
        while (stream.hasNext()) {
            caracteresNaoRepetidos =
                            caracteresNaoRepetidos.add(String.format("%s", stream.getNext()));
        }
        String resultado = caracteresNaoRepetidos.toString();

        if (log.isDebugEnabled()) {
            log.debug("ValidadorCaracter. Texto: {} - Resultado: {}", texto, resultado);
        }

        return resultado;
    }

}
