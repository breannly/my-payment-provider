package com.example.myuserservice.service;

import com.example.myuserservice.dto.IndividualDetailsDto;
import com.example.myuserservice.dto.IndividualDto;
import com.example.myuserservice.dto.IndividualNewDto;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IndividualService {

    Mono<IndividualDto> register(IndividualNewDto individualNewDto);

    Mono<IndividualDetailsDto> findById(UUID individualId);
}
