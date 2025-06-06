package br.com.presenca.controle.domain.repository;

import br.com.presenca.controle.domain.entity.Atividade;
import br.com.presenca.controle.domain.entity.Presenca;

import java.util.List;
import java.util.Optional;

public interface AtividadeRepository {
    Optional<Atividade> findById(String atividadeId);

    void salvarPresenca(Presenca presenca);

    void save(Atividade atividade);

    List<Atividade> findAll();
}
