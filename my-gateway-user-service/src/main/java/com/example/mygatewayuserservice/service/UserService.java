package com.example.mygatewayuserservice.service;

import com.example.mypaymentprovider.api.individual.IndividualDetailsDto;
import com.example.mypaymentprovider.api.individual.IndividualNewDto;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<IndividualDetailsDto> save(IndividualNewDto individualNewDto);
}
