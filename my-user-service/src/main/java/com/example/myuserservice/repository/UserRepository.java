package com.example.myuserservice.repository;

import com.example.myuserservice.entity.user.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository extends R2dbcRepository<User, Long> {

    Mono<User> findByProfileId(UUID uid);

    Mono<User> findUserByEmail(String email);
}
