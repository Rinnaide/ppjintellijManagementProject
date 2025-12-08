package com.senac.projectmanagement.dto;

public class UserLoginResponseDTO {

    private Long usuario_id;
    private String usuario_nome;
    private String usuario_token;

    // Constructors, getters, setters

    public UserLoginResponseDTO() {}

    public UserLoginResponseDTO(Long usuario_id, String usuario_nome, String usuario_token) {
        this.usuario_id = usuario_id;
        this.usuario_nome = usuario_nome;
        this.usuario_token = usuario_token;
    }

    public Long getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Long usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getUsuario_nome() {
        return usuario_nome;
    }

    public void setUsuario_nome(String usuario_nome) {
        this.usuario_nome = usuario_nome;
    }

    public String getUsuario_token() {
        return usuario_token;
    }

    public void setUsuario_token(String usuario_token) {
        this.usuario_token = usuario_token;
    }
}
