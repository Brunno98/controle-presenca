package br.com.presenca.controle.infraestructure.db.memory;

import br.com.presenca.controle.domain.entity.Usuario;
import br.com.presenca.controle.domain.repository.UsuarioRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Profile("inMemory")
@Repository
public class UsuarioRepositoryInMemory implements UsuarioRepository {
    private static final Usuario DEFAULT_USER = new Usuario("1");

    private final Map<String, Usuario> usuarios;

    public UsuarioRepositoryInMemory() {
        final var map = new HashMap<String, Usuario>();
        map.put(DEFAULT_USER.getId(), DEFAULT_USER);
        this.usuarios = map;
    }

    @Override
    public Optional<Usuario> findById(String userId) {
        return Optional.ofNullable(this.usuarios.get(userId));
    }
}
