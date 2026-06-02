package com.example.medicalapiproject.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_DOCTOR, ROLE_PATIENT, ROLE_RECEPTIONIST;

    @Override
    public String getAuthority() {
        return name();
    }
}
