package com.leverx.learningmanagementsystem.core.security.role;

import lombok.Getter;

public enum Role {

    MANAGER("MANAGER"), USER("USER");

    @Getter
    private final String value;

    Role(String value) {
        this.value = value;
    }
}
