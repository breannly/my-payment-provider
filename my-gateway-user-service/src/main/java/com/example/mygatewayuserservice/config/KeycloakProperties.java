package com.example.mygatewayuserservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class KeycloakProperties {

    @Value("${api.keycloak.auth-server-url}")
    private String serverURL;
    @Value("${api.keycloak.realm}")
    private String realm;
    @Value("${api.keycloak.resource}")
    private String clientID;
    @Value("${api.keycloak.credentials.secret}")
    private String clientSecret;

    @Value("${api.keycloak.admin.realm}")
    private String adminRealm;
    @Value("${api.keycloak.admin.resource}")
    private String adminClientID;
    @Value("${api.keycloak.admin.username}")
    private String adminUsername;
    @Value("${api.keycloak.admin.password}")
    private String adminPassword;

}
