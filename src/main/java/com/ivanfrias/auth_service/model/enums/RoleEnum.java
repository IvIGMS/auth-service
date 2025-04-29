package com.ivanfrias.auth_service.model.enums;

public enum RoleEnum {
    MANAGER("MANAGER"),
    ADMIN("ADMIN");

    private final String role;

    RoleEnum(String role) {
        this.role = role;
    }

    public String getValue() {
        return role;
    }
}
