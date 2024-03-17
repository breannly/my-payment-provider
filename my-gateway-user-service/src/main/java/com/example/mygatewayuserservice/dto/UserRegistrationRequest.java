package com.example.mygatewayuserservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserRegistrationRequest {

    @NotNull
    private String username;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String confirmPassword;
    @NotNull
    private String firstName;
    @NotNull
    private String secondName;
}
