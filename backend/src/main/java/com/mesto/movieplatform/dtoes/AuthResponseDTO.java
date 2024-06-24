package com.mesto.movieplatform.dtoes;

import com.mesto.movieplatform.entities.User;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer";
    private Integer userId;

    public AuthResponseDTO(String accessToken, Integer userId) {
        this.accessToken = accessToken;
        this.userId = userId;
    }
}
