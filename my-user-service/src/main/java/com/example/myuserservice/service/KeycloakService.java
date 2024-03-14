package com.example.myuserservice.service;

import com.example.myuserservice.entity.user.User;
import org.keycloak.representations.AccessTokenResponse;
import reactor.core.publisher.Mono;

public interface KeycloakService {

    Mono<AccessTokenResponse> save(User user);
}
