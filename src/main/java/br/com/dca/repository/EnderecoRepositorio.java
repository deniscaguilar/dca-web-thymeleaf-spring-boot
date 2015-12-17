package br.com.dca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dca.domain.Endereco;

/**
 * Contrato com as operações para a entidade Endereco.
 * 
 * 
 * @author Dênis Casado Aguilar
 *
 */
@Repository
public interface EnderecoRepositorio extends JpaRepository<Endereco, Long> {

    /**
     * Realiza a consulta de endereço, usando como filtros os campos obrigatórios.
     * 
     * @param logradouro
     * @param bairro
     * @param cidade
     * @param estado
     * @return lista de endereços de acordo com os filtros informados.
     */
    List<Endereco> findByLogradouroAndNumeroAndCidadeAndEstado(String logradouro, String numero,
                    String cidade, String estado);

}
