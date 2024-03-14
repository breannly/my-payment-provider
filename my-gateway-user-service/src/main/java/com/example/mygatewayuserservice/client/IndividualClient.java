package com.example.mygatewayuserservice.client;

import com.example.mypaymentprovider.api.auth.AccessTokenDto;
import com.example.mypaymentprovider.api.individual.IndividualDetailsDto;
import com.example.mypaymentprovider.api.individual.IndividualNewDto;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IndividualClient {

    Mono<AccessTokenDto> register(IndividualNewDto individualNewDto);

    Mono<IndividualDetailsDto> findById(UUID individualId);
}
