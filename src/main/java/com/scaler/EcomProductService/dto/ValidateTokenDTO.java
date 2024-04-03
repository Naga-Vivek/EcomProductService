package com.scaler.EcomProductService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateTokenDTO {
    private Long userId;
    private String token;

    public ValidateTokenDTO(){

    }

    public ValidateTokenDTO(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }
}
