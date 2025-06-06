package br.com.presenca.controle.application.usecase;

import br.com.presenca.controle.domain.entity.Atividade;
import br.com.presenca.controle.domain.repository.AtividadeRepository;
import br.com.presenca.controle.application.command.CriarAtividadeCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CriarAtividadeUseCase {

    private final AtividadeRepository atividadeRepository;

    public String execute(CriarAtividadeCommand command) {
        final var descricao = command.descricao();
        final var tempoDeConclusao = command.tempoDeConclusao();

        Atividade atividade = Atividade.create(descricao, tempoDeConclusao);

        atividadeRepository.save(atividade);

        return atividade.getId();
    }

}
