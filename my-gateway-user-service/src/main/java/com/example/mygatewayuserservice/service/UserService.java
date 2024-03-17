package com.example.mygatewayuserservice.service;

import com.example.mygatewayuserservice.dto.UserGetMeResponse;
import com.example.mypaymentprovider.api.individual.IndividualNewRequest;
import com.example.mypaymentprovider.api.individual.IndividualShortResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService {

    Mono<IndividualShortResponse> save(IndividualNewRequest request);

    Mono<UserGetMeResponse> findById(UUID id);
}
