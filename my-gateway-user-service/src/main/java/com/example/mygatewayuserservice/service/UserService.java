package com.example.mygatewayuserservice.service;

import com.example.mygatewayuserservice.dto.UserGetMeResponse;
import com.example.mypaymentprovider.api.individual.model.Individual;
import com.example.mypaymentprovider.api.individual.request.IndividualCreateRequest;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService {

    Mono<Individual> save(IndividualCreateRequest request);

    Mono<UserGetMeResponse> findById(UUID id);
}
