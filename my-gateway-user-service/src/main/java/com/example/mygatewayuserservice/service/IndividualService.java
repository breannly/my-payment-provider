package com.example.mygatewayuserservice.service;

import com.example.mypaymentprovider.api.individual.model.Individual;
import com.example.mypaymentprovider.api.individual.request.IndividualCreateRequest;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IndividualService {

    Mono<Individual> save(IndividualCreateRequest request);

    Mono<Individual> findById(UUID id);
}
