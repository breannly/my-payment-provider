package com.example.mygatewayuserservice.client;

import com.example.mypaymentprovider.api.individual.request.IndividualCreateRequest;
import com.example.mypaymentprovider.api.individual.response.IndividualCreateResponse;
import com.example.mypaymentprovider.api.individual.response.IndividualGetByIdResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IndividualClient {

    Mono<IndividualCreateResponse> save(IndividualCreateRequest request);

    Mono<IndividualGetByIdResponse> findById(UUID id);
}
