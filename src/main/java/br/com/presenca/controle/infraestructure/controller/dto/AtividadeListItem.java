package br.com.presenca.controle.infraestructure.controller.dto;

import br.com.presenca.controle.domain.entity.Atividade;
import br.com.presenca.controle.domain.entity.Usuario;

public record AtividadeListItem(
        String id,
        String descricao,
        int minutosDeConclusao,
        String status
) {
    public static AtividadeListItem from(Atividade atividade) {
        return new AtividadeListItem(
                atividade.getId(),
                atividade.getDescricao(),
                atividade.getTempoDeConclusao(),
                null
        );
    }

    public static AtividadeListItem withStatus(Atividade atividade, Usuario usuario) {
        final var status = atividade.getStatusUsuario(usuario);

        return new AtividadeListItem(
                atividade.getId(),
                atividade.getDescricao(),
                atividade.getTempoDeConclusao(),
                status.name()
        );
    }
}
