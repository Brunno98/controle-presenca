package br.com.presenca.controle.application.command;

public record CriarAtividadeCommand(
    String descricao,
    int tempoDeConclusao
) {}
