package com.example.myuserservice.service;

import com.example.mypaymentprovider.api.individual.request.IndividualCreateRequest;
import com.example.mypaymentprovider.api.individual.response.IndividualCreateResponse;
import com.example.mypaymentprovider.api.individual.response.IndividualGetByIdResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IndividualService {

    Mono<IndividualCreateResponse> create(IndividualCreateRequest request);

    Mono<IndividualGetByIdResponse> findById(UUID id);
}
