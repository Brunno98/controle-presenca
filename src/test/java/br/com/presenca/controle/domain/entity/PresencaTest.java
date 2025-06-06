package br.com.presenca.controle.domain.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Stream;

class PresencaTest {

    private Usuario usuario = new Usuario("id");
    private Atividade atividade = new Atividade("id", "descricao", 1, new ArrayList<>());
    private LocalDateTime fixedClock = LocalDateTime.of(2025, 1, 15, 12, 0, 0);

    @Test
    @DisplayName("A presença mais recente deve ser a primeira em uma lista ordenada")
    void compare() {
        final var presencaMaisRecente = Presenca.registra(usuario, atividade, fixedClock.plusMinutes(2));
        final var presencaMediana = Presenca.registra(usuario, atividade, fixedClock.plusMinutes(1));
        final var presencaMaisAntiga = Presenca.registra(usuario, atividade, fixedClock);

        final var presencas = Stream.of(presencaMediana, presencaMaisRecente, presencaMaisAntiga).sorted().toList();

        Assertions.assertEquals(presencas.get(0).getHorario(), presencaMaisRecente.getHorario());
        Assertions.assertEquals(presencas.get(1).getHorario(), presencaMediana.getHorario());
        Assertions.assertEquals(presencas.get(2).getHorario(), presencaMaisAntiga.getHorario());
    }

}