package br.com.dca.util;

import java.util.Set;
import java.util.StringJoiner;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * Classe utilitária com as funcionalidades para extrair as informações lançadas nas exceptions e
 * retornar o resultado em uma String.
 * 
 * @author Dênis Casado Aguilar
 */
public class ExceptionUtil {

    private ExceptionUtil() {}

    public static String extrairMensagem(Exception ex) {
        if (ex == null) {
            return null;
        }

        if (ex instanceof ConstraintViolationException) {
            return extrairMensagemExceptionContraint((ConstraintViolationException) ex);
        }

        return ex.getMessage();
    }

    private static String extrairMensagemExceptionContraint(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> contraintViolations = ex.getConstraintViolations();
        StringJoiner sj = new StringJoiner(", ");

        if (contraintViolations != null && !contraintViolations.isEmpty()) {
            for (ConstraintViolation<?> v : contraintViolations) {
                sj = sj.add(String.format("%s", v.getMessage()));
            }
        }
        return sj.toString();
    }

}
