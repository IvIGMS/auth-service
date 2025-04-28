package com.ivanfrias.auth_service.dto;

public record AuthenticationRequest(
        String email,
        String password
) {}

