package br.com.presenca.controle.application.usecase;

import br.com.presenca.controle.domain.entity.Atividade;
import br.com.presenca.controle.domain.entity.Usuario;
import br.com.presenca.controle.domain.repository.AtividadeRepository;
import br.com.presenca.controle.domain.repository.UsuarioRepository;
import br.com.presenca.controle.application.command.MarcarPresencaCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarcarPresencaUseCase {

    private final AtividadeRepository atividadeRepository;
    private final UsuarioRepository usuarioRepository;

    public void execute(MarcarPresencaCommand command) {
        final var userId = command.getUserId();
        final var atividadeId = command.getAtividadeId();

        Usuario usuario = usuarioRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario "+userId+" nao encontrado"));
        Atividade atividade = atividadeRepository.findById(atividadeId).orElseThrow(() -> new RuntimeException("Atividade "+atividadeId+" nao encontrado"));

        final var presenca = atividade.marcarPresenca(usuario);

        atividadeRepository.salvarPresenca(presenca);
    }

}
