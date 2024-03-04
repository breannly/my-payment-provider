package com.example.myuserservice.controller;

import com.example.myuserservice.dto.IndividualDto;
import com.example.myuserservice.dto.IndividualNewDto;
import com.example.myuserservice.service.IndividualService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/individuals")
public class IndividualController {

    private final IndividualService individualService;

    @PostMapping
    public Mono<ResponseEntity<IndividualDto>> register(@RequestBody @Validated IndividualNewDto individualNewDto) {
        return Mono.defer(() -> {
            log.info("Register Individual: {}", individualNewDto);
            return individualService.register(individualNewDto)
                    .doOnSuccess(response -> log.info("Registered Individual: {}", response))
                    .doOnError(error -> log.error("Register Individual Error: {}", error.getMessage()))
                    .map(individualDto -> new ResponseEntity<>(individualDto, HttpStatus.OK));
        });
    }
}
