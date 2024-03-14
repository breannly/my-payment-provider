package com.example.myuserservice.service.impl;

import com.example.myuserservice.entity.profile.Profile;
import com.example.myuserservice.entity.user.User;
import com.example.myuserservice.provider.KeycloakProvider;
import com.example.myuserservice.service.KeycloakService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {

    @Value("${api.keycloak.realm}")
    private String realm;

    private final KeycloakProvider keycloakProvider;

    @Override
    public Mono<AccessTokenResponse> save(User user) {
        return Mono.fromCallable(() -> {
            UserRepresentation userRepresentation = createUserRepresentation(user);
            createUserInKeycloak(userRepresentation);
            return retrieveUserTokens(user);
        });
    }

    private UserRepresentation createUserRepresentation(User user) {
        Profile profile = user.getProfile();
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(profile.getUsername());
        userRepresentation.setCredentials(List.of(createCredentialRepresentation(profile.getPassword())));
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setEnabled(user.getProfile().getEnabled());

        log.debug("Mapped User object to UserRepresentation for username: {}", profile.getUsername());
        return userRepresentation;
    }

    private void createUserInKeycloak(UserRepresentation userRepresentation) {
        try (Response response = keycloakProvider.getAdminInstance().realm(realm).users().create(userRepresentation)) {
            if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
                log.error("User creation failed with status: {}", response.getStatus());
                throw new RuntimeException("User creation failed with status: " + response.getStatus());
            }
            log.info("User created successfully in Keycloak.");
        }
    }

    private CredentialRepresentation createCredentialRepresentation(String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);

        log.debug("Created CredentialRepresentation for password.");
        return credentialRepresentation;
    }

    private AccessTokenResponse retrieveUserTokens(User user) {
        Profile profile = user.getProfile();
        AccessTokenResponse accessTokenResponse = keycloakProvider
                .getInstanceWithPasswordCredentials(profile.getUsername(), profile.getPassword())
                .tokenManager()
                .getAccessToken();

        log.info("Retrieved access tokens for username: {}", profile.getUsername());
        return accessTokenResponse;
    }
}

