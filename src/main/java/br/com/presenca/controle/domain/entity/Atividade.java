package br.com.presenca.controle.domain.entity;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Atividade {

    @Getter
    private String id;
    @Getter
    private String descricao;
    @Getter
    private int tempoDeConclusao; // Minutos que o usuário deve ficar esperar entre a entrada e conclusao
    private List<Presenca> presencas;

    private Atividade(String id, String descricao, int tempoDeConclusao, List<Presenca> presencas) {
        this.id = id;
        this.descricao = descricao;
        this.tempoDeConclusao = tempoDeConclusao;
        this.presencas = presencas;
    }

    public static Atividade create(String descricao, int tempoDeConclusao) {
        final var id = UUID.randomUUID().toString();
        return new Atividade(id, descricao, tempoDeConclusao, new ArrayList<>());
    }

    public Presenca marcarPresenca(Usuario usuario) {
        final var optionalPresenca = presencas.stream()
                .sorted()
                .filter(p -> p.usuarioPresente(usuario))
                .findFirst();
        if (optionalPresenca.isEmpty()) {
            final var presenca = Presenca.registra(usuario, this);
            this.presencas.add(presenca);
            return presenca;
        }

        final var ultimaPresenca = optionalPresenca.get();
        final var passouTempoDeTolerancia = LocalDateTime.now().isAfter(
                ultimaPresenca.getHorario().plusMinutes(tempoDeConclusao));
        if (passouTempoDeTolerancia) {
            final var presenca = Presenca.registra(usuario, this);
            this.presencas.add(presenca);
            return presenca;
        }
        //TODO: Criar exception de dominio
        throw new RuntimeException("Tentativa de marcar nova presença ainda dentro do tempo de tolerancia");
    }

    public boolean contem(Presenca presenca) {
        return this.presencas.contains(presenca);
    }
}
