package br.com.dca.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.dca.util.ExceptionUtil;

/**
 * Responsável por gerenciar as exceções lançadas e/ou geradas pela camada controller.
 * 
 * @author Dênis Casado Aguilar
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public void handleIllegalArgumentException(HttpServletResponse response, Exception ex)
                    throws IOException {
        log.error("Falhou, exceção lançada: {} ", ex.getMessage());
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    public void handleDataAccessException(HttpServletResponse response, Exception ex)
                    throws IOException {
        log.error("Ocorreram problemas no acesso a dados: {} ", ex.getMessage());
        String msg = String.format("Ocorreram problemas no acesso a dados: %s", ex.getMessage());
        response.sendError(HttpStatus.BAD_GATEWAY.value(), msg);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public void handleConstraintViolationException(HttpServletResponse response, Exception ex)
                    throws IOException {
        String exMsg = ExceptionUtil.extrairMensagem(ex);
        log.error("{Validação de campos}Foram identificados problemas no preenchimento de campos.");
        String msg = String.format("Campos obrigatórios devem ser preenchidos: %s", exMsg);
        response.sendError(HttpStatus.BAD_REQUEST.value(), msg);
    }

}
