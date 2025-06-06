package br.com.presenca.controle.domain.exception;

import br.com.presenca.controle.domain.entity.Atividade;

public class TempoDeToleranciaNaoAtingidoException extends RuntimeException {
    public TempoDeToleranciaNaoAtingidoException(Atividade atividade) {
        super("Tentativa de marcar nova presença na atividade " +atividade.getId()+ " ainda dentro do tempo de tolerância");
    }
} 