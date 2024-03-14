package com.example.mygatewayuserservice.rest;

import com.example.mygatewayuserservice.client.IndividualClient;
import com.example.mypaymentprovider.api.auth.AccessTokenDto;
import com.example.mypaymentprovider.api.individual.IndividualDetailsDto;
import com.example.mypaymentprovider.api.individual.IndividualNewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class RestV1AuthController {

    private final IndividualClient individualClient;

    @PostMapping("api/v1/registration")
    public Mono<ResponseEntity<AccessTokenDto>> registration(@RequestBody IndividualNewDto individualNewDto) {
        return individualClient.register(individualNewDto)
                .flatMap(response -> Mono.just(ResponseEntity.ok(response)));
    }

    @GetMapping("api/v1/individuals/{individualId}/details")
    public Mono<ResponseEntity<IndividualDetailsDto>> findById(@PathVariable UUID individualId) {
        return individualClient.findById(individualId)
                .flatMap(response -> Mono.just(ResponseEntity.ok(response)));
    }
}
