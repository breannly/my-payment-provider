package com.example.mygatewayuserservice.service.impl;

import com.example.mygatewayuserservice.client.IndividualClient;
import com.example.mygatewayuserservice.dto.UserGetMeResponse;
import com.example.mygatewayuserservice.exception.IndividualServiceException;
import com.example.mygatewayuserservice.mapper.IndividualMapper;
import com.example.mygatewayuserservice.service.UserService;
import com.example.mypaymentprovider.api.individual.model.Individual;
import com.example.mypaymentprovider.api.individual.model.Status;
import com.example.mypaymentprovider.api.individual.request.IndividualCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IndividualServiceImpl implements UserService {

    private final IndividualClient individualClient;
    private final IndividualMapper individualMapper;

    @Override
    public Mono<Individual> save(IndividualCreateRequest request) {
        return individualClient.save(request)
                .flatMap(response -> {
                    if (response.getStatus() != Status.SUCCESS || response.getDescription() != null) {
                        return Mono.error(new IndividualServiceException(HttpStatus.CONFLICT, response.getDescription()));
                    }
                    return Mono.just(response.getIndividual());
                });
    }

    @Override
    public Mono<UserGetMeResponse> findById(UUID id) {
        return individualClient.findById(id)
                .flatMap(response -> {
                    if (response.getStatus() != Status.SUCCESS || response.getDescription() != null) {
                        return Mono.error(new IndividualServiceException(HttpStatus.CONFLICT, response.getDescription()));
                    }
                    return Mono.just(response.getIndividual());
                })
                .map(individualMapper::map);
    }
}
