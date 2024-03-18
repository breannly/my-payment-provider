package com.example.mygatewayuserservice.client.impl;

import com.example.mygatewayuserservice.client.common.AbstractClient;
import com.example.mygatewayuserservice.client.IndividualClient;
import com.example.mypaymentprovider.api.individual.request.IndividualCreateRequest;
import com.example.mypaymentprovider.api.individual.response.IndividualCreateResponse;
import com.example.mypaymentprovider.api.individual.response.IndividualGetByIdResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
public class IndividualClientImpl extends AbstractClient implements IndividualClient {

    protected IndividualClientImpl(@Value("${api.client.individual.url}") String url,
                                   @Value("${api.client.individual.timeoutInSeconds}") Long timeoutInSeconds) {
        super(url, timeoutInSeconds);
    }

    @Override
    protected Logger log() {
        return log;
    }

    @Override
    public Mono<IndividualCreateResponse> save(IndividualCreateRequest request) {
        log.info("Request: {}", request);
        return execute(HttpMethod.POST, request, "", IndividualCreateResponse.class)
                .doOnSuccess(response -> log.info("Response: {}", response));
    }

    @Override
    public Mono<IndividualGetByIdResponse> findById(UUID id) {
        log.info("User Service - Find individual by id: {}", id);
        return execute(HttpMethod.GET, "/individual/" + id.toString() + "/details", IndividualGetByIdResponse.class)
                .doOnSuccess(response -> log().info("Response: {}", response));
    }
}
