package com.example.mygatewayuserservice.service.impl;

import com.example.mygatewayuserservice.dto.UserGetMeResponse;
import com.example.mygatewayuserservice.mapper.IndividualMapper;
import com.example.mygatewayuserservice.service.IndividualService;
import com.example.mygatewayuserservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final IndividualService individualService;
    private final IndividualMapper individualMapper;

    @Override
    public Mono<UserGetMeResponse> findById(UUID id) {
        return individualService.findById(id)
                .map(individualMapper::map);
    }
}
