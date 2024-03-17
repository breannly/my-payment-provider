package com.example.mygatewayuserservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserLoginResponse {

    private String accessToken;
    private Long expiresIn;
    private String refreshToken;
    private String tokenType;
}
