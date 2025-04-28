package com.ivanfrias.auth_service.dto;

public record RegisterRequest(
        String email,
        String password,
        String firstname,
        String lastname
) {}

