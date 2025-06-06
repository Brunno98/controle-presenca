package br.com.presenca.controle.domain.exception;

import br.com.presenca.controle.domain.entity.Atividade;

public class AtividadeFechadaException extends RuntimeException {
    private final Atividade atividade;

    public AtividadeFechadaException(Atividade atividade) {
        super(String.format("Atividade '%s' está fechada e não aceita novos participantes", atividade.getDescricao()));
        this.atividade = atividade;
    }

    public Atividade getAtividade() {
        return atividade;
    }
} 