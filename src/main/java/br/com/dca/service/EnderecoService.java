package br.com.dca.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import br.com.dca.domain.Endereco;
import br.com.dca.domain.ListaEnderecos;
import br.com.dca.repository.EnderecoRepositorio;

/**
 * Serviço com as funcionalidades relacionadas a manipulação de Endereço.
 * 
 * 
 * @author Dênis Casado Aguilar
 */
@Service
@Validated
@Transactional(rollbackFor = Exception.class)
public class EnderecoService {

    private static final Logger log = LoggerFactory.getLogger(EnderecoService.class);

    @Autowired
    private EnderecoRepositorio enderecoRepositorio;

    public Endereco salvar(@NotNull @Valid Endereco endereco) {
        if (log.isDebugEnabled()) {
            log.debug("Gravando endereço: {}", endereco);
        }
        List<Endereco> enderecos = enderecoRepositorio.findByLogradouroAndNumeroAndCidadeAndEstado(
                        endereco.getLogradouro(), endereco.getNumero(), endereco.getCidade(),
                        endereco.getEstado());

        for (Endereco enderecoGravado : enderecos) {
            if (enderecoGravado.getId().equals(endereco.getId())) {
                continue;
            }
            Preconditions.checkArgument(!enderecoGravado.equals(endereco),
                            String.format("Endereço com o id: (%s) já cadastrado com os dados informados.",
                                            enderecoGravado.getId()));
        }
        return enderecoRepositorio.saveAndFlush(endereco);
    }

    public void remover(@NotNull Long enderecoId) {
        if (log.isDebugEnabled()) {
            log.debug("Endereço sendo excluído: {}", enderecoId);
        }
        enderecoRepositorio.delete(enderecoId);
    }

    @Transactional(readOnly = true)
    public Optional<Endereco> buscarPorId(@NotNull Long enderecoId) {
        Endereco endereco = enderecoRepositorio.findOne(enderecoId);
        return Optional.ofNullable(endereco);
    }

    @Transactional(readOnly = true)
    public ListaEnderecos buscarTodos() {
        return new ListaEnderecos(Lists.newArrayList(enderecoRepositorio.findAll()));
    }

}
