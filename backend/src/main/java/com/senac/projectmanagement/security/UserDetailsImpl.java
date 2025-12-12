package com.senac.projectmanagement.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.senac.projectmanagement.entity.User;

public class UserDetailsImpl implements UserDetails {

    private Long idUsuario;
    private String nomeUsuario;
    private String email;
    private String password;
    private Boolean userIsActive;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long idUsuario, String nomeUsuario, String email, String password, Boolean userIsActive, Collection<? extends GrantedAuthority> authorities) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.password = password;
        this.userIsActive = userIsActive;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        String nomeUsuario = user.getUserFirstName() + " " + user.getUserLastName();
        return new UserDetailsImpl(
                user.getUserId(),
                nomeUsuario,
                user.getUserEmail(),
                user.getUserPasswordHash(),
                user.getUserIsActive(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userIsActive != null ? userIsActive : false;
    }
}
