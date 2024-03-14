package com.example.mygatewayuserservice.service;

import com.example.mygatewayuserservice.provider.KeycloakContext;
import org.keycloak.representations.AccessTokenResponse;
import reactor.core.publisher.Mono;

public interface KeycloakService {

    Mono<AccessTokenResponse> save(KeycloakContext context);

    Mono<AccessTokenResponse> refreshToken(KeycloakContext context);
}
