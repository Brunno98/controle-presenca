package br.com.presenca.controle.domain.entity;

import br.com.presenca.controle.domain.exception.AtividadeFechadaException;
import br.com.presenca.controle.domain.exception.TempoDeToleranciaNaoAtingidoException;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Atividade {

    private String id;
    private String descricao;
    private int tempoDeConclusao; // Minutos que o usuário deve ficar esperar entre a entrada e conclusao
    private List<Presenca> presencas;
    private boolean isOpen = true;

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

    public void fechar() {
        this.isOpen = false;
    }

    public Presenca marcarPresenca(Usuario usuario) {
        return this.marcarPresenca(usuario, LocalDateTime.now());
    }

    public Presenca marcarPresenca(Usuario usuario, LocalDateTime horarioBase) {
        final var optionalPresenca = presencas.stream()
                .sorted()
                .filter(p -> p.usuarioPresente(usuario))
                .findFirst();
        if (optionalPresenca.isEmpty()) {
            if (!this.isOpen) {
                throw new AtividadeFechadaException(this);
            }
            final var presenca = Presenca.registra(usuario, this);
            this.presencas.add(presenca);
            return presenca;
        }

        final var ultimaPresenca = optionalPresenca.get();
        final var passouTempoDeTolerancia = horarioBase.isAfter(
                ultimaPresenca.getHorario().plusMinutes(tempoDeConclusao));
        if (passouTempoDeTolerancia) {
            final var presenca = Presenca.registra(usuario, this);
            this.presencas.add(presenca);
            return presenca;
        }
        throw new TempoDeToleranciaNaoAtingidoException(this);
    }

    public boolean contem(Presenca presenca) {
        return this.presencas.contains(presenca);
    }
}
