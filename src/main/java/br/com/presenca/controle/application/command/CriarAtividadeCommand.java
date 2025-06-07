package br.com.presenca.controle.application.command;

public record CriarAtividadeCommand(
        String usuarioId,
        String descricao,
        int tempoDeConclusao
) {
}
