package br.com.presenca.controle.infraestructure.security;

import br.com.presenca.controle.domain.entity.Cargo;
import br.com.presenca.controle.domain.entity.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSecurity implements UserDetails {

    private UUID id;
    private String username;
    private String password;
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.getRole()));
    }

    public Usuario toDomain() {
        final String id = this.id == null ? null : this.getId().toString();
        return new Usuario(id, Cargo.valueOf(this.role));
    }
}
