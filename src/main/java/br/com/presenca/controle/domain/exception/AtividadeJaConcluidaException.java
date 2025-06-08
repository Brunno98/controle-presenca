package br.com.presenca.controle.domain.exception;

import br.com.presenca.controle.domain.entity.Atividade;

public class AtividadeJaConcluidaException extends DomainException {
    public AtividadeJaConcluidaException(Atividade atividade) {
        super(String.format("Atividade %s já foi concluída", atividade.getId()));
    }
}
