package com.example.mygatewayuserservice.service.impl;

import com.example.mygatewayuserservice.dto.UserLoginRequest;
import com.example.mygatewayuserservice.dto.UserLoginResponse;
import com.example.mygatewayuserservice.dto.UserRegistrationRequest;
import com.example.mygatewayuserservice.dto.UserRegistrationResponse;
import com.example.mygatewayuserservice.exception.AuthServiceException;
import com.example.mygatewayuserservice.exception.ErrorStatus;
import com.example.mygatewayuserservice.mapper.UserMapper;
import com.example.mygatewayuserservice.mapper.ResponseMapper;
import com.example.mygatewayuserservice.provider.KeycloakContext;
import com.example.mygatewayuserservice.service.AuthService;
import com.example.mygatewayuserservice.service.KeycloakService;
import com.example.mygatewayuserservice.service.UserService;
import com.example.mygatewayuserservice.validator.UserRegistrationRequestValidator;
import com.example.mypaymentprovider.api.individual.IndividualNewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final KeycloakService keycloakService;
    private final UserMapper individualMapper;
    private final ResponseMapper responseMapper;

    @Override
    public Mono<UserRegistrationResponse> register(UserRegistrationRequest userRegistrationRequest) {
        return UserRegistrationRequestValidator.validate(userRegistrationRequest).toMono()
                .flatMap(result -> {
                    if (result.isError()) {
                        return Mono.error(new AuthServiceException(ErrorStatus.VALIDATION_ERROR, result.error()));
                    }
                    return Mono.just(userRegistrationRequest);
                })
                .flatMap(request -> {
                    IndividualNewRequest individualNewRequest = individualMapper.map(request);
                    individualNewRequest.setCreatedAt(LocalDateTime.now());
                    return userService.save(individualNewRequest);
                })
                .flatMap(response -> keycloakService.save(KeycloakContext.builder()
                        .userId(response.getId().toString())
                        .username(userRegistrationRequest.getUsername())
                        .email(userRegistrationRequest.getEmail())
                        .password(userRegistrationRequest.getPassword())
                        .firstName(userRegistrationRequest.getFirstName())
                        .secondName(userRegistrationRequest.getSecondName())
                        .build()))
                .map(responseMapper::mapUserRegistrationResponse);
    }

    @Override
    public Mono<UserLoginResponse> login(UserLoginRequest request) {
        return keycloakService.refreshToken(KeycloakContext.builder()
                        .username(request.getUsername())
                        .email(request.getEmail())
                        .password(request.getPassword())
                        .build())
                .map(responseMapper::mapUserLoginResponse);
    }
}
