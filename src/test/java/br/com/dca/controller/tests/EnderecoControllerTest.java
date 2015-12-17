package br.com.dca.controller.tests;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.dca.boot.Application;
import br.com.dca.domain.Cep;
import br.com.dca.domain.Endereco;


/**
 * Responsável pelos testes de EnderecoController.
 * 
 * 
 * @author Dênis Casado Aguilar
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("dev")
public class EnderecoControllerTest extends BaseControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    /**
     * Faz a consulta de endereços, sem indicar nenhum parâmetro. O resultado esperado é o retorno
     * com sucesso, com mais de um item no array.
     */
    @Test
    public void validarListaEnderecos() throws Exception {
        this.mvc.perform(get("/enderecos/").accept(MediaType.APPLICATION_JSON))
                        .andDo(setContentType("charset=utf-8")).andExpect(status().isOk())
                        .andExpect(jsonPath("enderecos", hasSize(greaterThanOrEqualTo(1))));
    }

    /**
     * Faz a consulta de um endereço com filtro por ID válido na base de dados. O resultado esperado
     * é o retorno de um endereço no formato Json.
     */
    @Test
    public void validarEnderecoCadastrado() throws Exception {
        Long id = 2l;
        this.mvc.perform(get(String.format("/enderecos/%s", id)).accept(MediaType.APPLICATION_JSON))
                        .andDo(setContentType("charset=utf-8")).andExpect(status().isOk())
                        .andExpect(jsonPath("logradouro", equalTo("Rua Braga")))
                        .andExpect(jsonPath("numero", equalTo("202")))
                        .andExpect(jsonPath("cidade", equalTo("São Bernardo do Campo")));
    }

    /**
     * Faz a consulta de um endereço com filtro por ID não cadastrado na base de dados. O resultado
     * esperado é o código HTTP 404 (NOT FOUND).
     */
    @Test
    public void validarEnderecoInexistente() throws Exception {
        Long id = 20l;
        this.mvc.perform(get(String.format("/enderecos/%s", id)).accept(MediaType.APPLICATION_JSON))
                        .andDo(setContentType("charset=utf-8")).andExpect(status().isNotFound())
                        .andDo(print());
    }

    /**
     * Faz a consulta de um endereço por ID cadastrado na base de dados, recebe os dados do endereço
     * no formato Json, modifica a informação de logradouro e envia o endereço modificado via método
     * UPDATE. O resultado esperado é o retorno com sucesso, código HTTP 200.
     */
    @Test
    public void validarAtualizacaoEnderecoCadastrado() throws Exception {
        Long id = 5l;
        MvcResult r = this.mvc
                        .perform(get(String.format("/enderecos/%s", id))
                                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(setContentType("charset=utf-8")).andExpect(status().isOk())
                        .andReturn();

        String json = r.getResponse().getContentAsString();
        Endereco e = converterJsonParaObjeto(json, Endereco.class);
        e.setLogradouro(e.getLogradouro() + " - modificado");

        this.mvc.perform(put("/enderecos").contentType(MediaType.APPLICATION_JSON)
                        .content(converterObjetoParaJsonBytes(e))
                        .accept(MediaType.APPLICATION_JSON)).andDo(setContentType("charset=utf-8"))
                        .andExpect(status().isOk());
    }

    /**
     * Faz a modificação de um endereço com o campo cep (obrigatório) vazio, envia o endereço
     * modificado pelo método UPDATE. O resultado esperado é durante a validação dos dados
     * obrigatórios do endereço seja barrado a atualização do endereço e retorne o código HTTP 400
     * (Bad Request).
     */
    @Test
    public void validarAtualizacaoEnderecoCadastradoInconsistente() throws Exception {
        Endereco e = new Endereco() {
            private static final long serialVersionUID = 1L;

            @Override
            public Long getId() {
                return 33l;
            }
        };
        e.setLogradouro("Teste Estado inconsistente - sem campos obrigatórios");

        MvcResult r = this.mvc
                        .perform(put("/enderecos").contentType(MediaType.APPLICATION_JSON)
                                        .content(converterObjetoParaJsonBytes(e))
                                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(setContentType("charset=utf-8")).andExpect(status().isBadRequest())
                        .andDo(print()).andReturn();

        assertTrue(r.getResponse().getErrorMessage().contains("Cep é obrigatório!"));
    }

    /**
     * Faz a modificação de um endereço com um ID inválido, envia a alteração do endereço através do
     * método UPDATE e por fim faz a busca do endereço pelo ID inexistente na base de dados. O
     * resultado esperado é que o método UPDATE seja executado, mas o método para buscar o endereço
     * (GET) retorne o código HTTP 404 (NOT FOUND).
     */
    @Test
    public void validarAtualizacaoEnderecoInexistente() throws Exception {
        Endereco e = new Endereco() {
            private static final long serialVersionUID = 1L;

            @Override
            public Long getId() {
                return 33l;
            }
        };
        e.setLogradouro("Teste Inexistente (id invalido)");
        e.setNumero("1");
        e.setCep("11111-111");
        e.setCidade("Sao Paulo");
        e.setEstado("SP");

        this.mvc.perform(put("/enderecos").contentType(MediaType.APPLICATION_JSON)
                        .content(converterObjetoParaJsonBytes(e))
                        .accept(MediaType.APPLICATION_JSON)).andDo(setContentType("charset=utf-8"))
                        .andExpect(status().isOk());

        this.mvc.perform(get(String.format("/enderecos/%s", e.getId()))
                        .accept(MediaType.APPLICATION_JSON)).andDo(setContentType("charset=utf-8"))
                        .andExpect(status().isNotFound()).andDo(print());
    }

    /**
     * Faz a modificação de um endereço sem informar um ID, envia a alteração do objeto endereco em
     * estado NOVO através do método UPDATE, o sistema deve recusar essa atualização. O resultado
     * esperado é o retorno do código HTTP 400 (BAD REQUEST). *
     */
    @Test
    public void validarAtualizacaoEnderecoEstadoNovo() throws Exception {
        Endereco e = new Endereco();
        e.setLogradouro("Teste Estado Novo (sem id)");
        e.setNumero("1");
        e.setCidade("Sao Paulo");
        e.setEstado("SP");

        this.mvc.perform(put("/enderecos").contentType(MediaType.APPLICATION_JSON)
                        .content(converterObjetoParaJsonBytes(e))
                        .accept(MediaType.APPLICATION_JSON)).andDo(setContentType("charset=utf-8"))
                        .andExpect(status().isBadRequest()).andDo(print());
    }

    /**
     * Executa o método DELETE informando como filtro um ID de endereço válido para excluir da base
     * de dados. O resultado esperado é que o endereço seja removido e o código retornado será HTTP
     * 200.
     */
    @Test
    public void validarExclusaoEnderecoCadastrado() throws Exception {
        Long id = 3l;
        this.mvc.perform(delete(String.format("/enderecos/%s", id))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                        .andDo(setContentType("charset=utf-8")).andExpect(status().isOk());
    }

    /**
     * Preenche um novo objeto com os dados do endereço e envia no formato Json. O resultado
     * esperado é que o registro seja inserido na base de dados e seja retornado o código HTTP 200 e
     * a informação com o ID gerado e inserido na base de dados.
     */
    @Test
    public void validarInclusaoEndereco() throws Exception {
        Endereco e = new Endereco();
        e.setLogradouro("Cadastro preenchido full");
        e.setNumero("1");
        e.setCep("11111000");
        e.setCidade("Sao Paulo");
        e.setEstado("SP");

        this.mvc.perform(post("/enderecos").contentType(MediaType.APPLICATION_JSON)
                        .content(converterObjetoParaJsonBytes(e))
                        .accept(MediaType.APPLICATION_JSON)).andDo(setContentType("charset=utf-8"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("id", greaterThanOrEqualTo(1)));
    }

    /**
     * Faz a consulta de Cep pelo número do Cep, usa o Json de retorno com os dados do Cep para
     * preencher parte das informações do objeto de endereço, preenche os dados restantes do
     * endereço e o envia para gravar na base de dados. O resultado esperado é que o registro seja
     * inserido na base de dados e seja retornado o código HTTP 200 e a informação do ID gerado e
     * gravado na base de dados.
     */
    @Test
    public void validarInclusaoEnderecoComPesquisaCepValido() throws Exception {
        String numeroCep = "01310200";
        MvcResult r = this.mvc
                        .perform(get(String.format("/ceps/%s", numeroCep))
                                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(setContentType("charset=utf-8")).andExpect(status().isOk())
                        .andReturn();

        String json = r.getResponse().getContentAsString();
        Cep c = converterJsonParaObjeto(json, Cep.class);

        Endereco e = new Endereco();
        e.popularComDadosCep(c);
        e.setNumero("505");
        e.setComplemento("Ed Paris - ap 11");

        this.mvc.perform(post("/enderecos").contentType(MediaType.APPLICATION_JSON)
                        .content(converterObjetoParaJsonBytes(e))
                        .accept(MediaType.APPLICATION_JSON)).andDo(setContentType("charset=utf-8"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("id", greaterThanOrEqualTo(1)));
    }

    /**
     * Faz a tentativa de inserir um endereço com um cep inválido, em formato diferente do permitido
     * (99999-999 ou 99999999). O resultado esperado é que o sistema recuse a inclusão do endereço e
     * seja retornado o código HTTP 400 (BAD REQUEST).
     */
    @Test
    public void validarInclusaoEnderecoComCepInvalido() throws Exception {
        Endereco e = new Endereco();
        e.setLogradouro("Conteudo do Cep é invalido");
        e.setNumero("1");
        e.setCep("a0b1c-2d3");
        e.setCidade("Pirapora");
        e.setEstado("DF");
        e.setNumero("6");
        e.setComplemento("sem precisão");

        MvcResult r = this.mvc
                        .perform(post("/enderecos").contentType(MediaType.APPLICATION_JSON)
                                        .content(converterObjetoParaJsonBytes(e))
                                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(setContentType("charset=utf-8")).andExpect(status().isBadRequest())
                        .andDo(print()).andReturn();

        assertTrue(r.getResponse().getErrorMessage()
                        .contains("Cep deve seguir o formato \\d{5}-?\\{d3}"));
    }

    /**
     * Faz a tentativa de inserir um novo endereço com as informações obrigatórias incompletas,
     * faltando o campo logradouro. O resultado esperado é que o sistema recuse essa inclusão do
     * endereço e seja retornado o código HTTP 400 (BAD REQUEST).
     */
    @Test
    public void validarInclusaoEnderecoInconsistente() throws Exception {
        Endereco e = new Endereco();

        MvcResult r = this.mvc
                        .perform(post("/enderecos").contentType(MediaType.APPLICATION_JSON)
                                        .content(converterObjetoParaJsonBytes(e))
                                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(setContentType("charset=utf-8")).andExpect(status().isBadRequest())
                        .andDo(print()).andReturn();

        assertTrue(r.getResponse().getErrorMessage().contains("Logradouro é obrigatório!"));
    }

}
