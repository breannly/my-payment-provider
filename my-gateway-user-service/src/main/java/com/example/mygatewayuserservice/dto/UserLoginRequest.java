package com.example.mygatewayuserservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserLoginRequest {

    private String username;
    @Email
    private String email;
    @NotNull
    private String password;
}
