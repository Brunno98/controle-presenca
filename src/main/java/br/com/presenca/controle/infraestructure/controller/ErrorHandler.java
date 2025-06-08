package br.com.presenca.controle.infraestructure.controller;

import br.com.presenca.controle.domain.exception.DomainException;
import br.com.presenca.controle.domain.exception.EntidadeNaoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ProblemDetail handle(EntidadeNaoEncontradaException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(DomainException.class)
    public ProblemDetail handle(DomainException e) {
        // Fixme: todos os domainException podem expor sua mensagem de erro? não me parece uma boa ideia...
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
    }

}
