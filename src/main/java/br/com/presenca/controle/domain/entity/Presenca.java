package br.com.presenca.controle.domain.entity;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Presenca implements Comparable<Presenca> {

    private String id;
    private String usuarioId;
    private String atividadeId;
    @Getter
    private LocalDateTime horario;

    private Presenca(String id, String usuarioId, String atividadeId, LocalDateTime horario) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.atividadeId = atividadeId;
        this.horario = horario;
    }

    public static Presenca registra(Usuario usuario, Atividade atividade) {
        final var agora = LocalDateTime.now();
        return Presenca.registra(usuario, atividade, agora);
    }

    public static Presenca registra(Usuario usuario, Atividade atividade, LocalDateTime horario) {
        final var id = UUID.randomUUID().toString();
        return new Presenca(id, usuario.getId(), atividade.getId(), horario);
    }

    public boolean usuarioPresente(Usuario usuario) {
        return usuario.possuiId(this.usuarioId);
    }

    /**
     * Por regra, as presenças mais recentes devem ter uma ordem de precedencia maior
     * que as presenças mais antigas, ou seja, em uma ordenação devem aparecer primeiro
     * que as mais antigas
     *
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Presenca o) {
        return Math.negateExact(this.horario.compareTo(o.getHorario()));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Presenca presenca = (Presenca) o;
        return Objects.equals(getId(), presenca.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
