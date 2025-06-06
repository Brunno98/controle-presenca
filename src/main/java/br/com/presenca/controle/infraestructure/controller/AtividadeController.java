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
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

    @GetMapping
    public List<AtividadeListItem> listAll() {
        return listarAtividadesUseCase.execute()
                .stream()
                .map(AtividadeListItem::from)
                .toList();
    }

    @PostMapping
    public CriarAtividadeResponse criarAtividade(@RequestBody CriarAtividadeRequest request) {
        final var descricao = request.descricao();
        final var tempoDeConclusao = request.tempoDeConclusao();
        final var command = new CriarAtividadeCommand(descricao, tempoDeConclusao);

        final var id = this.criarAtividadeUseCase.execute(command);

        return new CriarAtividadeResponse(id);
    }

    @PostMapping("presenca")
    public void marcarPresenca(@RequestHeader("X-USER-ID") String userId, @RequestBody MarcarPresencaRequest request) {
        final var command = new MarcarPresencaCommand(userId, request.atividadeId());
        this.marcarPresencaUseCase.execute(command);
    }

}
