package com.yedam.app.jwt.service;

import lombok.Getter;

@Getter
public class JwtResponseDTO {
    private final String token;

    public JwtResponseDTO(String token) {
        this.token = token;
    }
}
