package com.example.mygatewayuserservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserLoginRequest {

    private String username;
    private String email;
    private String password;
}
