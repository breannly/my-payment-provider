package com.example.mygatewayuserservice.service.impl;

import com.example.mygatewayuserservice.client.IndividualClient;
import com.example.mygatewayuserservice.dto.UserGetMeResponse;
import com.example.mygatewayuserservice.mapper.ResponseMapper;
import com.example.mygatewayuserservice.service.UserService;
import com.example.mypaymentprovider.api.individual.IndividualNewRequest;
import com.example.mypaymentprovider.api.individual.IndividualShortResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final IndividualClient individualClient;
    private final ResponseMapper responseMapper;

    @Override
    public Mono<IndividualShortResponse> save(IndividualNewRequest request) {
        return individualClient.save(request);
    }

    @Override
    public Mono<UserGetMeResponse> findById(UUID id) {
        return individualClient.findById(id)
                .map(responseMapper::mapUserGetMeResponse);
    }
}
