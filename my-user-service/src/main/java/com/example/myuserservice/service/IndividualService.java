package com.example.myuserservice.service;

import com.example.mypaymentprovider.api.auth.AccessTokenDto;
import com.example.myuserservice.dto.IndividualDetailsDto;
import com.example.myuserservice.dto.IndividualNewDto;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IndividualService {

    Mono<AccessTokenDto> save(IndividualNewDto individualNewDto);

    Mono<IndividualDetailsDto> findById(UUID individualId);
}
