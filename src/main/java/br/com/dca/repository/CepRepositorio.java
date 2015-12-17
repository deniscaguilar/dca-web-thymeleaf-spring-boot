package br.com.dca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dca.domain.Cep;

/**
 * Contrato com as operações para a entidade Cep.
 * 
 * 
 * @author Dênis Casado Aguilar
 */
@Repository
public interface CepRepositorio extends JpaRepository<Cep, Long> {

    /**
     * Realiza a consulta de cep, usando o número do cep como filtro.
     * 
     * @param numeroCep
     * @return
     */
    Cep findOneByNumeroCep(String numeroCep);

}
