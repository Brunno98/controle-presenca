package br.com.presenca.controle.infraestructure.db.memory;

import br.com.presenca.controle.domain.entity.Atividade;
import br.com.presenca.controle.domain.entity.Presenca;
import br.com.presenca.controle.domain.repository.AtividadeRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Profile("inMemory")
@Repository
public class AtividadeRepositoryInMemory implements AtividadeRepository {

    private final Map<String, Atividade> atividades = new HashMap<>();

    @Override
    public Optional<Atividade> findById(String atividadeId) {
        return Optional.ofNullable(this.atividades.get(atividadeId));
    }

    /*
    Metodo em memoria não faz muito sentido. Nesse caso vai apenas validar se a presença e a atividade estão relacionadas
     */
    @Override
    public void salvarPresenca(Presenca presenca) {
        final var atividade = this.findById(presenca.getAtividadeId())
                .orElseThrow(() -> new RuntimeException("Atividade de id " + presenca.getAtividadeId() + " não encontrada ao tentar salvar presenca"));
        if (!atividade.contem(presenca)) {
            throw new RuntimeException("Erro ao salvar presença! presença de id "+presenca.getId()+" não está relacionada a atividade de id "+presenca.getAtividadeId());
        }
    }

    @Override
    public void save(Atividade atividade) {
        this.atividades.put(atividade.getId(), atividade);
    }

    @Override
    public List<Atividade> findAll() {
        return new ArrayList<>(this.atividades.values());
    }
}
