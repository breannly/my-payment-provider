package com.example.mygatewayuserservice.service.impl;

import com.example.mygatewayuserservice.client.IndividualClient;
import com.example.mygatewayuserservice.service.UserService;
import com.example.mypaymentprovider.api.individual.IndividualDetailsDto;
import com.example.mypaymentprovider.api.individual.IndividualNewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final IndividualClient individualClient;

    @Override
    public Mono<IndividualDetailsDto> save(IndividualNewDto individualNewDto) {
        /* todo: необходимо проверить что пользователь уже существует перед тем как его сохранять
                 скорее всего нужно делать 2 запроса: поиск по email, поиск по username */
        return individualClient.register(individualNewDto);
    }
}
