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

import com.google.common.base.Preconditions;

import br.com.dca.domain.Endereco;
import br.com.dca.domain.EnderecoId;
import br.com.dca.domain.ListaEnderecos;
import br.com.dca.domain.Mensagem;
import br.com.dca.exception.ObjetoNaoEncontradoException;
import br.com.dca.service.EnderecoService;

/**
 * Controller Rest responsável por expor os serviços para a manipulação de endereços.
 * 
 * 
 * @author Dênis Casado Aguilar
 */
@RestController
@RequestMapping(value = "/enderecos")
public class EnderecoController {

    private static final Logger log = LoggerFactory.getLogger(EnderecoController.class);

    @Autowired
    private EnderecoService enderecoService;

    /**
     * Consulta todos os endereços cadastrados, retorna um Json com a lista de endereços. Pode
     * retornar uma lista de endereços vazia, caso não tenha nenhum endereço cadastrado.
     * 
     */
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ListaEnderecos buscarTodos() {
        ListaEnderecos listaEnderecos = enderecoService.buscarTodos();
        if (log.isDebugEnabled()) {
            log.debug("Total de endereços retornados: {} registro(s).",
                            listaEnderecos.getEnderecos().size());
        }
        return listaEnderecos;
    }

    /**
     * Consulta um endereço pelo ID (obrigatório) e retorna um Json com os dados do endereço
     * consultado. Retorna código HTTP 404 (NOT FOUND), caso não encontre o registro pelo ID.
     * 
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {
                    MediaType.APPLICATION_JSON_VALUE})
    public Endereco buscarPorId(@PathVariable Long id) {
        Optional<Endereco> optEndereco = enderecoService.buscarPorId(id);
        if (!optEndereco.isPresent()) {
            log.error("Endereco com id {}, não encontrado", id);
            throw new ObjetoNaoEncontradoException();
        }
        return optEndereco.get();
    }

    /**
     * Recebe um Json de entrada representando um endereço que será inserido na base de dados. Caso
     * exista algum problema de validação, retorna código HTTP 400 (BAD REQUEST).
     * 
     * @param endereco
     */
    @RequestMapping(method = RequestMethod.POST, consumes = {
                    MediaType.APPLICATION_JSON_VALUE}, produces = {
                                    MediaType.APPLICATION_JSON_VALUE})
    public EnderecoId salvar(@RequestBody Endereco endereco) {
        Preconditions.checkArgument(endereco.isNew(), "Problemas ao inserir o endereço");
        Endereco ederecoGravado = enderecoService.salvar(endereco);
        return new EnderecoId(ederecoGravado.getId());
    }

    /**
     * Recebe um Json de entrada representando um endereço já cadastrado que será atualizado na base
     * de dados. Caso exista algum problema de validação, retorna código HTTP 400 (BAD REQUEST).
     * 
     * @param endereco
     */
    @RequestMapping(method = RequestMethod.PUT, consumes = {
                    MediaType.APPLICATION_JSON_VALUE}, produces = {
                                    MediaType.APPLICATION_JSON_VALUE})
    public Mensagem atualizar(@RequestBody Endereco endereco) {
        Preconditions.checkArgument(!endereco.isNew(), "Problemas para atualizar o endereço");
        enderecoService.salvar(endereco);
        return new Mensagem("ok");
    }

    /**
     * Recebe um ID (obrigatório) que representa um ID do endereço que deverá ser excluído da base
     * de dados.
     * 
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Mensagem excluir(@PathVariable Long id) {
        enderecoService.remover(id);
        return new Mensagem("ok");
    }

}
