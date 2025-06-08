package br.com.presenca.controle.infraestructure.security;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Configuration
@RequiredArgsConstructor
@Profile("!prod")
public class UserDataInitializer {

    private final UsuarioSecurityService usuarioSecurityService;

    @PostConstruct
    public void init() {
        try {
            usuarioSecurityService.criarUsuario("admin", "admin", "ADMINISTRADOR");
            usuarioSecurityService.criarUsuario("user", "user", "USUARIO");
        } catch (Exception e) {
            log.error("erro ao inicializar usuarios", e);
        }
    }
} 