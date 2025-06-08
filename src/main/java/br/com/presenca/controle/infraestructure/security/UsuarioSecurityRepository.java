package br.com.presenca.controle.infraestructure.security;

import java.util.Optional;

public interface UsuarioSecurityRepository {
    Optional<UsuarioSecurity> findByUsername(String username);

    boolean existsByUsername(String username);

    UsuarioSecurity save(UsuarioSecurity usuario);
}
