package br.com.presenca.controle.domain.repository;

import br.com.presenca.controle.domain.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository {
    Optional<Usuario> findById(String userId);
}
