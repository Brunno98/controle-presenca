package br.com.presenca.controle.domain.exception;

public abstract class DomainException extends RuntimeException {
    public DomainException(String message) {
        // Ignora stack trace pra otimizar memoria e performance
        super(message, null, true, false);
    }
}
