package br.com.presenca.controle.domain.entity;

import lombok.Getter;

import java.util.List;

public class Usuario {

    @Getter
    private String id;

    public Usuario(String id) {
        this.id = id;
    }

    public boolean possuiId(String id) {
        return this.id.equals(id);
    }
}
