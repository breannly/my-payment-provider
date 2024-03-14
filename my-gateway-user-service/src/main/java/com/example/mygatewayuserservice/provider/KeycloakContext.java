package com.example.mygatewayuserservice.provider;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
public class KeycloakContext {

    private String username;
    private String email;
    private String password;
}
