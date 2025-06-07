package br.com.presenca.controle.domain.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(String usuarioId) {
        super(String.format("Usuario %s não encontrado", usuarioId));
    }
}
