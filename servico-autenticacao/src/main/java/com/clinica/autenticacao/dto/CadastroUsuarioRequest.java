package com.clinica.autenticacao.dto;

import jakarta.validation.constraints.NotBlank;

public class CadastroUsuarioRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String senha;

    @NotBlank
    private String role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
