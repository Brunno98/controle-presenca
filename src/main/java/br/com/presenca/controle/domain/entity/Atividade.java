package br.com.presenca.controle.domain.entity;

import br.com.presenca.controle.domain.exception.AtividadeFechadaException;
import br.com.presenca.controle.domain.exception.AtividadeJaConcluidaException;
import br.com.presenca.controle.domain.exception.TempoDeToleranciaNaoAtingidoException;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Atividade {
    private static final int QUANTIDADE_MINIMA_DE_PRESENCA = 2;

    private String id;
    private String descricao;
    private int tempoDeConclusao; // Minutos que o usuário deve ficar esperar entre a entrada e conclusao
    private List<Presenca> presencas;
    private boolean isOpen = true;
    private int presencasNecessarias; // Quantidade de presença necessária para considerar uma atividade concluída

    private Atividade(String id, String descricao, int tempoDeConclusao, List<Presenca> presencas, int presencasNecessarias) {
        this.id = id;
        this.descricao = descricao;
        this.tempoDeConclusao = tempoDeConclusao;
        this.presencas = presencas;
        this.presencasNecessarias = presencasNecessarias;
    }

    public static Atividade create(String descricao, int tempoDeConclusao) {
        return Atividade.create(descricao, tempoDeConclusao, QUANTIDADE_MINIMA_DE_PRESENCA);
    }

    public static Atividade create(String descricao, int tempoDeConclusao, int presencasNecessarias) {
        final var id = UUID.randomUUID().toString();
        return new Atividade(id, descricao, tempoDeConclusao, new ArrayList<>(), presencasNecessarias);
    }


    public void fechar() {
        this.isOpen = false;
    }

    public Presenca marcarPresenca(String usuarioId, LocalDateTime horarioBase) {
        final var presencasDoUsuario = presencas.stream()
                .filter(p -> p.usuarioPresente(usuarioId))
                .sorted()
                .toList();
        if (presencasDoUsuario.isEmpty()) {
            if (!this.isOpen) {
                throw new AtividadeFechadaException(this);
            }
            final var presenca = Presenca.registra(usuarioId, this.getId(), horarioBase);
            this.presencas.add(presenca);
            return presenca;
        }

        if (presencasDoUsuario.size() >= this.presencasNecessarias) {
            throw new AtividadeJaConcluidaException(this);
        }

        final var ultimaPresenca = presencasDoUsuario.getFirst();
        final var passouTempoDeTolerancia = horarioBase.isAfter(
                ultimaPresenca.getHorario().plusMinutes(tempoDeConclusao));
        if (passouTempoDeTolerancia) {
            final var presenca = Presenca.registra(usuarioId, this.getId(), horarioBase);
            this.presencas.add(presenca);
            return presenca;
        }
        throw new TempoDeToleranciaNaoAtingidoException(this);
    }

    public boolean contem(Presenca presenca) {
        return this.presencas.contains(presenca);
    }
}
