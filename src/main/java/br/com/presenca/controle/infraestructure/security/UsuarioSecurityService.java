package br.com.presenca.controle.infraestructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioSecurityService implements UserDetailsService {

    private final UsuarioSecurityRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public UsuarioSecurity criarUsuario(String username, String password, String role) {
        if (usuarioRepository.existsByUsername(username)) {
            throw new RuntimeException("Usuário já existe");
        }

        UsuarioSecurity usuario = UsuarioSecurity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();

        return usuarioRepository.save(usuario);
    }

} 