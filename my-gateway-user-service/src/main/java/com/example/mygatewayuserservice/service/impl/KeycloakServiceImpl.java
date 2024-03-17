package com.example.mygatewayuserservice.service.impl;

import com.example.mygatewayuserservice.exception.ErrorStatus;
import com.example.mygatewayuserservice.exception.KeycloakServiceException;
import com.example.mygatewayuserservice.provider.KeycloakContext;
import com.example.mygatewayuserservice.provider.KeycloakProvider;
import com.example.mygatewayuserservice.service.KeycloakService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {

    @Value("${api.keycloak.realm}")
    private String realm;

    private final KeycloakProvider keycloakProvider;

    @Override
    public Mono<AccessTokenResponse> save(KeycloakContext context) {
        return Mono.fromCallable(() -> {
            UserRepresentation userRepresentation = createUserRepresentation(context);
            createUserInKeycloak(userRepresentation);
            return retrieveUserTokens(context);
        }).timeout(Duration.ofSeconds(2L));
    }

    @Override
    public Mono<AccessTokenResponse> refreshToken(KeycloakContext context) {
        return Mono.fromCallable(() -> retrieveUserTokens(context));
    }

    private UserRepresentation createUserRepresentation(KeycloakContext context) {
        var attributes = Map.of("userId", List.of(context.getUserId()));
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(context.getUsername());
        userRepresentation.setEmail(context.getEmail());
        userRepresentation.setAttributes(attributes);
        userRepresentation.setFirstName(context.getFirstName());
        userRepresentation.setLastName(context.getSecondName());
        userRepresentation.setCredentials(List.of(createCredentialRepresentation(context.getPassword())));
        userRepresentation.setEnabled(true);

        return userRepresentation;
    }

    private void createUserInKeycloak(UserRepresentation userRepresentation) {
        try (Response response = keycloakProvider.getAdminInstance().realm(realm).users().create(userRepresentation)) {
            if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
                log.error("User creation failed with status: {}", response.getStatus());
                throw new KeycloakServiceException(ErrorStatus.KEYCLOAK_FAILED, "Failed to save user in Keycloak");
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

    private AccessTokenResponse retrieveUserTokens(KeycloakContext context) {
        String username = Optional.ofNullable(context.getUsername()).orElse(context.getEmail());
        return keycloakProvider
                .getInstanceWithCredentials(username, context.getPassword())
                .tokenManager()
                .getAccessToken();
    }
}

