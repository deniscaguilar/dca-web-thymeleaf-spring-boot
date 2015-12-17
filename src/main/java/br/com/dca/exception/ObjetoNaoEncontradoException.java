package br.com.dca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception que é convertida em Response 404 pelo Spring MVC.
 * 
 * @author Dênis Casado Aguilar
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Objeto não encontrado!")
public class ObjetoNaoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

}
