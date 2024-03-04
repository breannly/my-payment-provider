package com.example.myuserservice.service;

import com.example.myuserservice.dto.IndividualDto;
import com.example.myuserservice.dto.IndividualNewDto;
import reactor.core.publisher.Mono;

public interface IndividualService {

    Mono<IndividualDto> register(IndividualNewDto individualNewDto);
}
