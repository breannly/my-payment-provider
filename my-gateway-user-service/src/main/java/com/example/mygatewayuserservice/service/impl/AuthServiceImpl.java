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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final KeycloakService keycloakService;
    private final UserMapper individualMapper;
    private final ResponseMapper responseMapper;

    @Override
    public Mono<UserRegistrationResponse> register(UserRegistrationRequest request) {
        return UserRegistrationRequestValidator.validate(request).toMono()
                .flatMap(result -> {
                    if (result.isError()) {
                        return Mono.error(new AuthServiceException(ErrorStatus.VALIDATION_ERROR, result.error()));
                    }
                    return Mono.just(individualMapper.map(request));
                })
                .flatMap(userService::save)
                .flatMap(response -> keycloakService.save(KeycloakContext.builder()
                        .username(request.getUsername())
                        .username(request.getEmail())
                        .username(request.getPassword())
                        .build()))
                .map(responseMapper::map);
    }

    @Override
    public Mono<UserLoginResponse> login(UserLoginRequest request) {
        return null;
    }
}
