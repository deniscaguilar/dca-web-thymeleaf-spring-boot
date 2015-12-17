package br.com.dca.controller.tests;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.dca.boot.Application;
import br.com.dca.domain.Cep;

/**
 * Responsável pelos testes de CepController.
 * 
 * 
 * @author Dênis Casado Aguilar
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("dev")
public class CepControllerTest extends BaseControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    /**
     * Realiza a consulta de Cep usando um número de cep válido, no formato 99999999. O resultado
     * esperado é que a consulta seja processada com sucesso.
     */
    @Test
    public void buscarCepValido() throws Exception {
        String numeroCep = "09725160";
        this.mvc.perform(get(String.format("/ceps/%s", numeroCep))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                        .andDo(setContentType("charset=utf-8"))
                        .andExpect(jsonPath("cidade", equalTo("São Bernardo do Campo")))
                        .andExpect(jsonPath("estado", equalTo("SP")));
    }

    /**
     * Faz a consulta de Cep usando um número de cep válido, no formato Json {numeroCep: 99999999}.
     * O resultado esperado é que a consulta seja processada com sucesso.
     */
    @Test
    public void buscarCepValidoComJson() throws Exception {
        Cep c = new Cep("09725160");
        this.mvc.perform(get("/ceps").contentType(MediaType.APPLICATION_JSON)
                        .content(converterObjetoParaJsonBytes(c))
                        .accept(MediaType.APPLICATION_JSON)).andDo(setContentType("charset=utf-8"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("cidade", equalTo("São Bernardo do Campo")))
                        .andExpect(jsonPath("estado", equalTo("SP")));
    }

    /**
     * Faz a consulta de Cep usando um numero de cep válido, no formato 99999-999. O resultado
     * esperado é que a consulta seja processada com sucesso.
     */
    @Test
    public void buscarCepValidoComFormatacao() throws Exception {
        String numeroCep = "09770-420";
        this.mvc.perform(get(String.format("/ceps/%s", numeroCep))
                        .accept(MediaType.APPLICATION_JSON)).andDo(setContentType("charset=utf-8"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("cidade", equalTo("São Bernardo do Campo")))
                        .andExpect(jsonPath("estado", equalTo("SP")));
    }

    /**
     * Faz a consulta de Cep usando um número de cep válido, no formato Json {numeroCep: 99999-999}.
     * O resultado esperado é que a consulta seja processada com sucesso.
     */
    @Test
    public void buscarCepValidoComJsonEFormatacao() throws Exception {
        Cep c = new Cep("09770-420");
        this.mvc.perform(get("/ceps").contentType(MediaType.APPLICATION_JSON)
                        .content(converterObjetoParaJsonBytes(c))
                        .accept(MediaType.APPLICATION_JSON)).andDo(setContentType("charset=utf-8"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("cidade", equalTo("São Bernardo do Campo")))
                        .andExpect(jsonPath("estado", equalTo("SP")));
    }

    /**
     * Faz a consulta de Cep usando um número de cep válido, no formato 99999999. O resultado
     * esperado é que a consulta seja processada com sucesso, mas que o Cep retornado seja diferente
     * do enviado. O sistema não encontra o Cep solicitado, mas deverá trocar o último digito para 0
     * e fazer uma nova consulta consecutivamente até esgotar as tentativas.
     */
    @Test
    public void buscarCepProximidadePaulistaComJson() throws Exception {
        Cep c = new Cep("01310222");
        this.mvc.perform(get("/ceps").contentType(MediaType.APPLICATION_JSON)
                        .content(converterObjetoParaJsonBytes(c))
                        .accept(MediaType.APPLICATION_JSON)).andDo(setContentType("charset=utf-8"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("bairro", equalTo("Paulista")))
                        .andExpect(jsonPath("cidade", equalTo("São Paulo")))
                        .andExpect(jsonPath("estado", equalTo("SP")))
                        .andExpect(jsonPath("numeroCep", not(c.getNumeroCep())));
    }

    /**
     * Faz a consulta do Cep usando um número de cep inválido, um texto qualquer. O resultado
     * esperado é o código HTTP 400 (BAD REQUEST).
     */
    @Test
    public void buscarCepInvalido() throws Exception {
        String numeroCep = "abcdefgh";
        this.mvc.perform(get(String.format("/ceps/%s", numeroCep))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                        .andDo(print());
    }

    /**
     * Faz a consulta de Cep sem enviar o número do cep. O resultado esperado é o código HTTP 415
     * (UNSUPORTTED MEDIA TYPE)
     */
    @Test
    public void buscarCepSemNumeroCep() throws Exception {
        this.mvc.perform(get("/ceps/").accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isUnsupportedMediaType()).andDo(print());
    }

    /**
     * Faz a consulta de Cep usando um número de cep válido, mas que não esta cadastrado na base de
     * Cep. O resultado esperado é código HTTP 404 (NOT FOUND).
     * 
     * @throws Exception
     */
    @Test
    public void buscarCepInexistente() throws Exception {
        String numeroCep = "01000-000";
        this.mvc.perform(get(String.format("/ceps/%s", numeroCep))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
                        .andDo(print());
    }

}
