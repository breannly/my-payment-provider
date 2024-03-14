package com.example.myuserservice.provider;

import com.example.myuserservice.config.KeycloakProperties;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeycloakProvider {

    private final KeycloakProperties keycloakProperties;

    private static Keycloak keycloak = null;

    public Keycloak getAdminInstance() {
        if (keycloak == null) {
            keycloak = KeycloakBuilder.builder()
                    .serverUrl(keycloakProperties.getServerURL())
                    .realm(keycloakProperties.getAdminRealm())
                    .clientId(keycloakProperties.getAdminClientID())
                    .username(keycloakProperties.getAdminUsername())
                    .password(keycloakProperties.getAdminPassword())
                    .grantType(OAuth2Constants.PASSWORD)
                    .build();
        }
        return keycloak;
    }

    public Keycloak getInstanceWithPasswordCredentials(String username, String password) {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakProperties.getServerURL())
                .realm(keycloakProperties.getRealm())
                .clientId(keycloakProperties.getClientID())
                .clientSecret(keycloakProperties.getClientSecret())
                .username(username)
                .password(password)
                .build();
    }

}
