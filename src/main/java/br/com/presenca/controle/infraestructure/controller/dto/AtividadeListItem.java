package br.com.presenca.controle.infraestructure.controller.dto;

import br.com.presenca.controle.domain.entity.Atividade;

public record AtividadeListItem(
        String id,
        String descricao,
        int minutosDeConclusao
) {
    public static AtividadeListItem from(Atividade atividade) {
        return new AtividadeListItem(
                atividade.getId(),
                atividade.getDescricao(),
                atividade.getTempoDeConclusao()
        );
    }
}
