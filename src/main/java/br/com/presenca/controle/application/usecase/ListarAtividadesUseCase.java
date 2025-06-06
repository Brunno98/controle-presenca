package br.com.presenca.controle.application.usecase;

import br.com.presenca.controle.domain.entity.Atividade;
import br.com.presenca.controle.domain.repository.AtividadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarAtividadesUseCase {

    private final AtividadeRepository atividadeRepository;

    public List<Atividade> execute() {
        return this.atividadeRepository.findAll();
    }

}
