package br.com.dca.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.google.common.base.Preconditions;

import br.com.dca.domain.Cep;
import br.com.dca.repository.CepRepositorio;
import br.com.dca.util.CepUtil;

/**
 * Serviço com as funcionalidades relacionadas a consulta de CEP.
 * 
 * 
 * @author Dênis Casado Aguilar
 */
@Service
@Validated
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CepService {

    private static final Logger log = LoggerFactory.getLogger(CepService.class);

    @Autowired
    private CepRepositorio cepRepositorio;

    public Optional<Cep> buscarPorNumero(@NotNull @NotEmpty String cep) {
        Preconditions.checkArgument(cep.matches("\\d{5}-?\\d{3}"), "Cep Inválido!");
        return buscarPorNumero(CepUtil.gerarSufixoParaCep(cep.replace("-", "")));
    }

    private Optional<Cep> buscarPorNumero(Collection<String> listaNumerosCeps) {
        int tentativa = 0;
        Cep cep = null;
        for (String nroCep : listaNumerosCeps) {
            if (!"".equals(nroCep == null ? "" : nroCep)) {
                if (log.isDebugEnabled())
                    log.debug("Buscando Cep pelo número {}, tentativa {}.", nroCep, ++tentativa);

                cep = cepRepositorio.findOneByNumeroCep(nroCep);
                if (cep != null) {
                    return Optional.of(cep);
                }
            }
        }
        return Optional.empty();
    }

}
