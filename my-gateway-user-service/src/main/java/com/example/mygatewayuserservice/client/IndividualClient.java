package com.example.mygatewayuserservice.client;

import com.example.mypaymentprovider.api.individual.IndividualDetailsResponse;
import com.example.mypaymentprovider.api.individual.IndividualNewRequest;
import com.example.mypaymentprovider.api.individual.IndividualShortResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IndividualClient {

    Mono<IndividualShortResponse> save(IndividualNewRequest request);

    Mono<IndividualDetailsResponse> findById(UUID id);
}
