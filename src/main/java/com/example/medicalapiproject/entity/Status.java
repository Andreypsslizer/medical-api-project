package com.example.medicalapiproject.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Status implements GrantedAuthority {
    CREATED, CONFIRMED, COMPLETED, CANCELLED;

    @Override
    public String getAuthority() {
        return name();
    }
}
