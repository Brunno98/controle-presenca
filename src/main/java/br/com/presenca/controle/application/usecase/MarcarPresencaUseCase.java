package br.com.presenca.controle.application.usecase;

import br.com.presenca.controle.domain.entity.Atividade;
import br.com.presenca.controle.domain.entity.Usuario;
import br.com.presenca.controle.domain.exception.EntidadeNaoEncontradaException;
import br.com.presenca.controle.domain.repository.AtividadeRepository;
import br.com.presenca.controle.domain.repository.UsuarioRepository;
import br.com.presenca.controle.application.command.MarcarPresencaCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MarcarPresencaUseCase {

    private final AtividadeRepository atividadeRepository;
    private final UsuarioRepository usuarioRepository;

    public void execute(MarcarPresencaCommand command) {
        final var userId = command.getUserId();
        final var atividadeId = command.getAtividadeId();

        //TODO: Melhorar semantica da validação de usuário existente
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuario", userId));
        Atividade atividade = atividadeRepository.findById(atividadeId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Atividade", atividadeId));

        final var presenca = atividade.marcarPresenca(usuario.id(), LocalDateTime.now());

        atividadeRepository.salvarPresenca(presenca);
    }

}
