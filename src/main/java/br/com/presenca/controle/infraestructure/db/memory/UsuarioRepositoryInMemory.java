package br.com.presenca.controle.infraestructure.db.memory;

import br.com.presenca.controle.domain.entity.Cargo;
import br.com.presenca.controle.domain.entity.Usuario;
import br.com.presenca.controle.domain.repository.UsuarioRepository;
import br.com.presenca.controle.infraestructure.security.UsuarioSecurity;
import br.com.presenca.controle.infraestructure.security.UsuarioSecurityRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Profile("inMemory")
@Repository
public class UsuarioRepositoryInMemory implements UsuarioRepository, UsuarioSecurityRepository {
//    private static final UsuarioJpa DEFAULT_USER = new UsuarioJpa("e41df59b-f117-4f89-9fbf-16a65ec470bc", "user@email.com", "pass" Cargo.USUARIO);
//    private static final UsuarioJpa DEFAULT_ADMIN = new UsuarioJpa("29bfa88d-3862-4c31-8fd9-5f114a47c4ce", Cargo.ADMINISTRADOR);

    private final Map<String, UsuarioSecurity> usuarios;

    public UsuarioRepositoryInMemory() {
        final var map = new HashMap<String, UsuarioSecurity>();
//        map.put(DEFAULT_USER.id(), DEFAULT_USER);
//        map.put(DEFAULT_ADMIN.id(), DEFAULT_ADMIN);
        this.usuarios = map;
    }

    @Override
    public Optional<Usuario> findById(String userId) {
        return Optional.ofNullable(this.usuarios.get(userId))
                .map(usuarioSecurity -> new Usuario(usuarioSecurity.getId().toString(), Cargo.valueOf(usuarioSecurity.getRole())));
    }

    @Override
    public Optional<UsuarioSecurity> findByUsername(String username) {
        return this.usuarios.values()
                .stream()
                .filter(usuario -> username.equals(usuario.getUsername()))
                .findFirst();
    }

    @Override
    public boolean existsByUsername(String username) {
        return this.findByUsername(username).isPresent();
    }

    @Override
    public UsuarioSecurity save(UsuarioSecurity usuario) {
        if (usuario.getId() == null) {
            usuario.setId(UUID.randomUUID());
        }
        return this.usuarios.put(usuario.getId().toString(), usuario);
    }
}
