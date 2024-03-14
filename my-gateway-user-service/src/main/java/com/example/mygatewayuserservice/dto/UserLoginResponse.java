package com.example.mygatewayuserservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginResponse {

    private String accessToken;
    private Long expiresIn;
    private String refreshToken;
    private String tokenType;
    private String message;
}
