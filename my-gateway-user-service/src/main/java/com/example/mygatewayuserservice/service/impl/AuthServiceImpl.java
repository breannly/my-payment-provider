package com.example.mygatewayuserservice.service.impl;

import com.example.mygatewayuserservice.dto.UserLoginRequest;
import com.example.mygatewayuserservice.dto.UserLoginResponse;
import com.example.mygatewayuserservice.dto.UserRegistrationRequest;
import com.example.mygatewayuserservice.dto.UserRegistrationResponse;
import com.example.mygatewayuserservice.exception.AuthServiceException;
import com.example.mygatewayuserservice.mapper.IndividualMapper;
import com.example.mygatewayuserservice.mapper.AuthMapper;
import com.example.mygatewayuserservice.provider.KeycloakContext;
import com.example.mygatewayuserservice.service.AuthService;
import com.example.mygatewayuserservice.service.KeycloakService;
import com.example.mygatewayuserservice.service.UserService;
import com.example.mygatewayuserservice.validator.UserRegistrationRequestValidator;
import com.example.mypaymentprovider.api.individual.request.IndividualCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final KeycloakService keycloakService;
    private final IndividualMapper individualMapper;
    private final AuthMapper authMapper;

    @Override
    public Mono<UserRegistrationResponse> register(UserRegistrationRequest userRegistrationRequest) {
        return UserRegistrationRequestValidator.validate(userRegistrationRequest).toMono()
                .flatMap(result -> {
                    if (result.isError()) {
                        return Mono.error(new AuthServiceException(HttpStatus.BAD_REQUEST, result.error()));
                    }
                    return Mono.just(userRegistrationRequest);
                })
                .flatMap(request -> {
                    IndividualCreateRequest individualCreateRequest = individualMapper.map(request);
                    individualCreateRequest.setCreatedAt(LocalDateTime.now());
                    return userService.save(individualCreateRequest);
                })
                .flatMap(individual -> keycloakService.save(KeycloakContext.builder()
                        .userId(individual.getUid().toString())
                        .username(userRegistrationRequest.getUsername())
                        .email(userRegistrationRequest.getEmail())
                        .password(userRegistrationRequest.getPassword())
                        .firstName(userRegistrationRequest.getFirstName())
                        .secondName(userRegistrationRequest.getSecondName())
                        .build()))
                .map(authMapper::mapUserRegistrationResponse);
    }

    @Override
    public Mono<UserLoginResponse> login(UserLoginRequest request) {
        return keycloakService.refreshToken(KeycloakContext.builder()
                        .username(request.getUsername())
                        .email(request.getEmail())
                        .password(request.getPassword())
                        .build())
                .map(authMapper::mapUserLoginResponse);
    }
}
