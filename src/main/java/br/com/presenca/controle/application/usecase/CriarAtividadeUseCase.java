package br.com.presenca.controle.application.usecase;

import br.com.presenca.controle.domain.entity.Atividade;
import br.com.presenca.controle.domain.entity.Usuario;
import br.com.presenca.controle.domain.exception.UsuarioNaoEncontradoException;
import br.com.presenca.controle.domain.exception.UsuarioSemPermisaoParaAcao;
import br.com.presenca.controle.domain.repository.AtividadeRepository;
import br.com.presenca.controle.application.command.CriarAtividadeCommand;
import br.com.presenca.controle.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CriarAtividadeUseCase {

    private final UsuarioRepository usuarioRepository;
    private final AtividadeRepository atividadeRepository;

    public String execute(CriarAtividadeCommand command) {
        final var usuarioId = command.usuarioId();
        final var descricao = command.descricao();
        final var tempoDeConclusao = command.tempoDeConclusao();

        final var usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));

        if (!usuario.isAdministrador()) {
            throw new UsuarioSemPermisaoParaAcao(usuarioId, "Criar atividade");
        }

        Atividade atividade = Atividade.create(descricao, tempoDeConclusao);

        atividadeRepository.save(atividade);

        return atividade.getId();
    }

}
