package com.example.myuserservice.service.impl;

import com.example.mypaymentprovider.api.individual.IndividualDetailsResponse;
import com.example.mypaymentprovider.api.individual.IndividualNewRequest;
import com.example.mypaymentprovider.api.individual.IndividualShortResponse;
import com.example.myuserservice.entity.Individual;
import com.example.myuserservice.entity.ProfileHistory;
import com.example.myuserservice.entity.profile.Profile;
import com.example.myuserservice.entity.profile.ProfileType;
import com.example.myuserservice.entity.user.User;
import com.example.myuserservice.entity.user.UserStatus;
import com.example.myuserservice.mapper.IndividualMapper;
import com.example.myuserservice.mapper.ProfileMapper;
import com.example.myuserservice.mapper.UserMapper;
import com.example.myuserservice.repository.IndividualRepository;
import com.example.myuserservice.repository.ProfileHistoryRepository;
import com.example.myuserservice.repository.ProfileRepository;
import com.example.myuserservice.repository.UserRepository;
import com.example.myuserservice.service.IndividualService;
import com.example.myuserservice.utils.JsonUtils;
import com.example.myuserservice.validator.IndividualNewRequestValidator;
import io.r2dbc.postgresql.codec.Json;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndividualServiceImpl implements IndividualService {

    private static final Profile EMPTY_PROFILE = new Profile();

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final IndividualRepository individualRepository;
    private final ProfileHistoryRepository profileHistoryRepository;
    private final ProfileMapper profileMapper;
    private final UserMapper userMapper;
    private final IndividualMapper individualMapper;
    private final TransactionalOperator transactionalOperator;

    @Override
    public Mono<IndividualShortResponse> save(IndividualNewRequest individualNewRequest) {
        return IndividualNewRequestValidator.validate(individualNewRequest).toMono()
                .flatMap(result -> {
                    if (result.isError()) {
                        return Mono.error(new RuntimeException());
                    }
                    return Mono.just(individualNewRequest);
                })
                .flatMap(request -> profileRepository.findProfileByUsername(request.getUsername())
                        .flatMap(result -> Mono.error(new RuntimeException()))
                        .switchIfEmpty(userRepository.findUserByEmail(request.getEmail())
                                .flatMap(result -> Mono.error(new RuntimeException()))
                                .switchIfEmpty(Mono.just(request))
                        )
                )
                .flatMap(request -> transactionalOperator.transactional(
                        createProfile((IndividualNewRequest) request)
                                .flatMap(profile -> createUser((IndividualNewRequest) request, profile))
                                .flatMap(user -> createProfileHistory(user).thenReturn(user))
                                .flatMap(user -> createIndividual((IndividualNewRequest) request, user))
                ))
                .map(individualMapper::map);
    }

    private Mono<Profile> createProfile(IndividualNewRequest request) {
        Profile profile = profileMapper.map(request).toBuilder()
                .enabled(true)
                .profileType(ProfileType.INDIVIDUAL)
                .isPasswordSet(Objects.nonNull(request.getPassword()))
                .build();

        log.info("Creating profile for individual: {}", profile);
        return profileRepository.save(profile)
                .doOnSuccess(savedProfile -> log.info("Profile saved successfully: {}", savedProfile))
                .doOnError(throwable -> log.error("Failed to create profile for individual: {}", throwable.getMessage()));
    }

    private Mono<User> createUser(IndividualNewRequest request, Profile profile) {
        User user = userMapper.map(request).toBuilder()
                .profileId(profile.getId())
                .profile(profile)
                .status(UserStatus.IS_PENDING)
                .filled(false)
                .build();

        log.info("Creating user for individual: {}", user);
        return userRepository.save(user)
                .doOnSuccess(savedUser -> log.info("User saved successfully: {}", savedUser))
                .doOnError(throwable -> log.error("Failed to create user for individual: {}", throwable.getMessage()));
    }

    private Mono<Individual> createIndividual(IndividualNewRequest request, User user) {
        Individual individual = individualMapper.map(request).toBuilder()
                .userId(user.getId())
                .user(user)
                .build();

        log.info("Creating individual for individual: {}", individual);
        return individualRepository.save(individual)
                .doOnSuccess(savedIndividual -> log.info("Individual saved successfully: {}", savedIndividual))
                .doOnError(throwable -> log.error("Failed to create individual for individual: {}", throwable.getMessage()));
    }

    private Mono<User> createProfileHistory(User user) {
        ProfileHistory profileHistory = ProfileHistory.builder()
                .profileId(user.getProfileId())
                .userId(user.getId())
                .createdAt(user.getCreatedAt())
                .changedValues(Json.of(JsonUtils.diff(EMPTY_PROFILE, user.getProfile())))
                .build();

        log.info("Saving profile history: {}", profileHistory);
        return profileHistoryRepository.save(profileHistory)
                .doOnSuccess(savedProfileHistory -> log.info("Profile history saved successfully: {}", savedProfileHistory))
                .doOnError(throwable -> log.error("Failed to save profile history: {}", throwable.getMessage()))
                .thenReturn(user);
    }

    @Override
    public Mono<IndividualDetailsResponse> findById(UUID id) {
        return profileRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("")))
                .flatMap(profile -> userRepository.findByProfileId(profile.getId())
                        .map(user -> user.toBuilder().profile(profile).build()))
                .flatMap(user -> individualRepository.findByUserId(user.getId())
                        .map(individual -> individual.toBuilder().user(user).build()))
                .map(individualMapper::mapToIndividualDetailsResponse);
    }
}
