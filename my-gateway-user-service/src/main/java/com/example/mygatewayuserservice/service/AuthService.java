package com.example.mygatewayuserservice.service;

import com.example.mygatewayuserservice.dto.UserLoginRequest;
import com.example.mygatewayuserservice.dto.UserLoginResponse;
import com.example.mygatewayuserservice.dto.UserRegistrationRequest;
import com.example.mygatewayuserservice.dto.UserRegistrationResponse;
import reactor.core.publisher.Mono;

public interface AuthService {

    Mono<UserRegistrationResponse> register(UserRegistrationRequest request);

    Mono<UserLoginResponse> login(UserLoginRequest request);
}
