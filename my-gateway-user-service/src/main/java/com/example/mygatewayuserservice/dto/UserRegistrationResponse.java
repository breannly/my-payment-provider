package com.example.mygatewayuserservice.dto;

import lombok.*;

@Getter
@Setter
@ToString
public class UserRegistrationResponse {

    private String accessToken;
    private Long expiresIn;
    private String refreshToken;
    private String tokenType;
}
