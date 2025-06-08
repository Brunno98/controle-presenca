package br.com.presenca.controle.domain.exception;

public class UsuarioSemPermisaoParaAcao extends DomainException {
    public UsuarioSemPermisaoParaAcao(String usuarioId, String acao) {
        super(String.format("Usuario %s sem permisao para realizar a acao: %s", usuarioId, acao));
    }
}
