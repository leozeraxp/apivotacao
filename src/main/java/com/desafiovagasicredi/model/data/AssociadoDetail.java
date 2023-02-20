package com.desafiovagasicredi.model.data;

import com.desafiovagasicredi.model.entity.Associado;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class AssociadoDetail implements UserDetails {

    private final Associado associado;

    public AssociadoDetail(Associado associado) {
        this.associado = associado;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return associado.getSenha();
    }

    @Override
    public String getUsername() {
        return associado.getCpf();
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
        return true;
    }
}
