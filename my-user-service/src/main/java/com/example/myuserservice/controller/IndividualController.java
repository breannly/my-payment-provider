package com.example.myuserservice.controller;

import com.example.myuserservice.dto.IndividualDetailsDto;
import com.example.myuserservice.dto.IndividualDto;
import com.example.myuserservice.dto.IndividualNewDto;
import com.example.myuserservice.service.IndividualService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/individuals")
public class IndividualController {

    private final IndividualService individualService;

    @PostMapping
    public Mono<ResponseEntity<IndividualDto>> save(@RequestBody @Validated IndividualNewDto individualNewDto) {
        log.info("Save Individual: {}", individualNewDto);
        return individualService.register(individualNewDto)
                .doOnSuccess(response -> log.info("Saved Individual: {}", response))
                .doOnError(error -> log.error("Save Individual Error: {}", error.getMessage()))
                .map(individualDto -> new ResponseEntity<>(individualDto, HttpStatus.CREATED));
    }

    @GetMapping("/individual/{individualId}/details")
    public Mono<ResponseEntity<IndividualDetailsDto>> findById(@PathVariable UUID individualId) {
        log.info("Find Individual By Id: {}", individualId);
        return individualService.findById(individualId)
                .doOnSuccess(response -> log.info("Found Individual: {}", response))
                .doOnError(error -> log.error("Found Individual Error: {}", error.getMessage()))
                .map(individualDetailsDto -> new ResponseEntity<>(individualDetailsDto, HttpStatus.OK));
    }
}
