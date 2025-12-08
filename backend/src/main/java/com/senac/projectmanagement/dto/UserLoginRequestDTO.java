package com.senac.projectmanagement.dto;

public class UserLoginRequestDTO {

    private String usuarioEmail;
    private String usuario_senha;

    // Constructors, getters, setters

    public UserLoginRequestDTO() {}

    public UserLoginRequestDTO(String usuarioEmail, String usuario_senha) {
        this.usuarioEmail = usuarioEmail;
        this.usuario_senha = usuario_senha;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public String getUsuario_senha() {
        return usuario_senha;
    }

    public void setUsuario_senha(String usuario_senha) {
        this.usuario_senha = usuario_senha;
    }
}
