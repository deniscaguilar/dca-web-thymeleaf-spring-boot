package br.com.dca.controller.tests;

import java.io.IOException;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe que define a estrutura básica para execução dos testes nos controllers.
 * 
 * @author Dênis Casado Aguilar
 */
public abstract class BaseControllerTest {

    protected static byte[] converterObjetoParaJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    protected static <T> T converterJsonParaObjeto(String json, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, clazz);
    }

    protected static SetContentTypeResultHandler setContentType(String contentType) {
        return new SetContentTypeResultHandler(contentType);
    }

    protected static class SetContentTypeResultHandler implements ResultHandler {
        private String contentType;

        private SetContentTypeResultHandler(String contentType) {
            this.contentType = contentType;
        }

        @Override
        public void handle(MvcResult result) throws Exception {
            result.getResponse().setContentType(contentType);
        }
    }
}
