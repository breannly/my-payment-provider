package com.example.mygatewayuserservice.provider;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
public class KeycloakContext {

    private String userId;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String secondName;
}
