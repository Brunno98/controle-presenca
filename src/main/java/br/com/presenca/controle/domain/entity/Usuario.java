package br.com.presenca.controle.domain.entity;

public record Usuario(String id, Cargo cargo) {

    public boolean isAdministrador() {
        return Cargo.ADMINISTRADOR.equals(this.cargo);
    }

}
