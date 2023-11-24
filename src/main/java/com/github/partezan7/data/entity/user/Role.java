package com.github.partezan7.data.entity.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    NONE,
    USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
