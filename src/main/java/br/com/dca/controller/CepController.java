package br.com.dca.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.dca.domain.Cep;
import br.com.dca.exception.ObjetoNaoEncontradoException;
import br.com.dca.service.CepService;

/**
 * Controller Rest responsável por expor os serviços para a consulta de endereço a partir do cep.
 * 
 * 
 * @author Dênis Casado Aguilar
 */
@RestController
@RequestMapping(value = "/ceps")
public class CepController {

    private static final Logger log = LoggerFactory.getLogger(CepController.class);

    @Autowired
    private CepService cepService;

    /**
     * Consulta as informações do CEP de acordo com o número do cep informado e retorna em formato
     * Json. Caso não encontre o dados do CEP é retornado ó código HTTP 404 (NOT FOUND), através da
     * exceção ObjetoNaoEncontradoException lançada. Se o número do cep informado não estiver nos
     * formatos 99999-999 ou 99999999 é retornado o código HTTP 400 (BAD REQUEST)
     * 
     * @param numeroCep
     */
    @RequestMapping(value = "/{numeroCep}", method = RequestMethod.GET, produces = {
                    MediaType.APPLICATION_JSON_VALUE})
    public Cep buscarPorNumeroCep(@PathVariable String numeroCep) {
        Optional<Cep> optCep = cepService.buscarPorNumero(numeroCep);
        if (!optCep.isPresent()) {
            log.error("CEP {} não encontrado", numeroCep);
            throw new ObjetoNaoEncontradoException();
        }
        return optCep.get();
    }

    /**
     * Consulta as informações do CEP de acordo com o filtro (Json de entrada) informado e retorna o
     * resultado também no formato Json.
     * 
     * @param filtro
     */
    @RequestMapping(method = RequestMethod.GET, consumes = {
                    MediaType.APPLICATION_JSON_VALUE}, produces = {
                                    MediaType.APPLICATION_JSON_VALUE})
    public Cep buscarPorFiltro(@RequestBody Cep filtro) {
        return buscarPorNumeroCep(filtro.getNumeroCep());
    }

}
