package br.com.presenca.controle.domain.exception;

public class EntidadeNaoEncontradaException extends RuntimeException {
    public EntidadeNaoEncontradaException(String tipo, String id) {
        super(String.format("%s de id %s não encontrado", tipo, id));
    }
} 