package com.example.mygatewayuserservice.service;

import com.example.mygatewayuserservice.dto.UserGetMeResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService {

    Mono<UserGetMeResponse> findById(UUID id);
}
