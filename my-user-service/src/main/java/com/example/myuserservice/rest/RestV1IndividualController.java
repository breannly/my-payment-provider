package com.example.myuserservice.rest;

import com.example.mypaymentprovider.api.individual.IndividualDetailsResponse;
import com.example.mypaymentprovider.api.individual.IndividualNewRequest;
import com.example.mypaymentprovider.api.individual.IndividualShortResponse;
import com.example.myuserservice.service.IndividualService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/individuals")
public class RestV1IndividualController {

    private final IndividualService individualService;

    @PostMapping
    public Mono<ResponseEntity<IndividualShortResponse>> save(@RequestBody IndividualNewRequest request) {
        log.info("Request: {}", request);
        return individualService.save(request)
                .map(response -> {
                    log.info("Response: {}", response);
                    return ResponseEntity.ok(response);
                });
    }

    @GetMapping("/individual/{individualId}/details")
    public Mono<ResponseEntity<IndividualDetailsResponse>> findById(@PathVariable("individualId") UUID id) {
        log.info("Find individual by id: {}", id);
        return individualService.findById(id)
                .map(response -> {
                    log.info("Response: {}", response);
                    return ResponseEntity.ok(response);
                });
    }
}
