package br.com.presenca.controle.infraestructure.controller;

import br.com.presenca.controle.application.command.CriarAtividadeCommand;
import br.com.presenca.controle.application.command.MarcarPresencaCommand;
import br.com.presenca.controle.application.usecase.CriarAtividadeUseCase;
import br.com.presenca.controle.application.usecase.ListarAtividadesUseCase;
import br.com.presenca.controle.application.usecase.MarcarPresencaUseCase;
import br.com.presenca.controle.infraestructure.controller.dto.AtividadeListItem;
import br.com.presenca.controle.infraestructure.controller.dto.CriarAtividadeRequest;
import br.com.presenca.controle.infraestructure.controller.dto.CriarAtividadeResponse;
import br.com.presenca.controle.infraestructure.controller.dto.MarcarPresencaRequest;
import br.com.presenca.controle.infraestructure.security.UsuarioSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("atividades")
public class AtividadeController {

    private final ListarAtividadesUseCase listarAtividadesUseCase;
    private final CriarAtividadeUseCase criarAtividadeUseCase;
    private final MarcarPresencaUseCase marcarPresencaUseCase;

    /*
     TODO:
      - levar logica de preencher status para dentro do useCase
      - Implementar filtros de listagem
      - Implementar paginação
     */
    @GetMapping
    public List<AtividadeListItem> listAll(@AuthenticationPrincipal UsuarioSecurity usuario) {
        return listarAtividadesUseCase.execute()
                .stream()
                .map(atividade -> {
                    if (usuario == null) {
                        return AtividadeListItem.from(atividade);
                    } else {
                        return AtividadeListItem.withStatus(atividade, usuario.toDomain());
                    }
                })
                .toList();
    }

    @PostMapping
    public CriarAtividadeResponse criarAtividade(@RequestBody CriarAtividadeRequest request, @AuthenticationPrincipal UsuarioSecurity usuario) {
        final var descricao = request.descricao();
        final var tempoDeConclusao = request.tempoDeConclusao();
        final var command = new CriarAtividadeCommand(usuario.getId().toString(), descricao, tempoDeConclusao);

        final var id = this.criarAtividadeUseCase.execute(command);

        return new CriarAtividadeResponse(id);
    }

    @PostMapping("presenca")
    public void marcarPresenca(@RequestBody MarcarPresencaRequest request, @AuthenticationPrincipal UsuarioSecurity usuario) {
        final var command = new MarcarPresencaCommand(usuario.getId().toString(), request.atividadeId());
        this.marcarPresencaUseCase.execute(command);
    }

}
