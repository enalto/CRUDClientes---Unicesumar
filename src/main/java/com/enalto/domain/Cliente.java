package com.enalto.domain;

import com.enalto.Email;

public class Cliente {
    private long id;
    private String nome;
    private Email email;
    private String telefone;

    public Cliente() {
    }

    public Cliente(String nome, Email email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public Email getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email=" + email +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}
