package br.com.presenca.controle.infraestructure.controller.dto;

public record CriarAtividadeRequest(
        String descricao,
        Integer tempoDeConclusao
) {
}
