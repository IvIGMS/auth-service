package com.ivanfrias.auth_service.dto;

import com.ivanfrias.auth_service.model.enums.RoleEnum;

public record RegisterRequest(
        String email,
        String password,
        String firstname,
        String lastname,
        Long storeId,
        RoleEnum role
) {}

