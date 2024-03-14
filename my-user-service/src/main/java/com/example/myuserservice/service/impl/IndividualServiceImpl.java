package com.example.myuserservice.service.impl;

import com.example.mypaymentprovider.api.auth.AccessTokenDto;
import com.example.myuserservice.dto.IndividualDetailsDto;
import com.example.myuserservice.dto.IndividualNewDto;
import com.example.myuserservice.entity.Individual;
import com.example.myuserservice.entity.user.User;
import com.example.myuserservice.entity.ProfileHistory;
import com.example.myuserservice.entity.profile.Profile;
import com.example.myuserservice.entity.profile.ProfileType;
import com.example.myuserservice.entity.user.UserStatus;
import com.example.myuserservice.mapper.IndividualMapper;
import com.example.myuserservice.mapper.ProfileMapper;
import com.example.myuserservice.mapper.UserMapper;
import com.example.myuserservice.repository.IndividualRepository;
import com.example.myuserservice.repository.ProfileHistoryRepository;
import com.example.myuserservice.repository.ProfileRepository;
import com.example.myuserservice.repository.UserRepository;
import com.example.myuserservice.service.IndividualService;
import com.example.myuserservice.service.KeycloakService;
import com.example.myuserservice.utils.JsonUtils;
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
    private final KeycloakService keycloakService;

    @Override
    public Mono<AccessTokenDto> save(IndividualNewDto individualNewDto) {
        return profileRepository.existsProfileByUsername(individualNewDto.getUsername())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new RuntimeException("Individual already exists"));
                    } else {
                        return transactionalOperator.transactional(
                                createProfile(individualNewDto)
                                        .flatMap(savedProfile -> createUser(individualNewDto, savedProfile))
                                        .flatMap(this::createProfileHistory)
                                        .flatMap(savedUser -> createIndividual(individualNewDto, savedUser))
                                        .flatMap(savedIndividual -> keycloakService.save(savedIndividual.getUser()))
                                        .map(accessTokenResponse -> {
                                            AccessTokenDto accessTokenDto = new AccessTokenDto();
                                            accessTokenDto.setAccessToken(accessTokenResponse.getToken());
                                            accessTokenDto.setExpiresIn(accessTokenResponse.getExpiresIn());
                                            accessTokenDto.setRefreshToken(accessTokenResponse.getRefreshToken());
                                            accessTokenDto.setTokenType(accessTokenResponse.getTokenType());
                                            return accessTokenDto;
                                        })
                        );
                    }
                });
    }

    private Mono<Profile> createProfile(IndividualNewDto individualNewDto) {
        Profile profile = profileMapper.map(individualNewDto).toBuilder()
                .enabled(true)
                .profileType(ProfileType.INDIVIDUAL)
                .isPasswordSet(Objects.nonNull(individualNewDto.getPassword()))
                .build();

        log.info("Creating profile for individual: {}", profile);
        return profileRepository.save(profile)
                .doOnSuccess(savedProfile -> log.info("Profile saved successfully: {}", savedProfile))
                .doOnError(throwable -> log.error("Failed to create profile for individual: {}", throwable.getMessage()));
    }

    private Mono<User> createUser(IndividualNewDto individualNewDto, Profile profile) {
        User user = userMapper.map(individualNewDto).toBuilder()
                .profileId(profile.getId())
                .profile(profile)
                .status(UserStatus.IS_PENDING)
                .filled(individualNewDto.getEmail() != null || individualNewDto.getPhoneNumber() != null)
                .build();

        log.info("Creating user for individual: {}", user);
        return userRepository.save(user)
                .doOnSuccess(savedUser -> log.info("User saved successfully: {}", savedUser))
                .doOnError(throwable -> log.error("Failed to create user for individual: {}", throwable.getMessage()));
    }

    private Mono<Individual> createIndividual(IndividualNewDto individualNewDto, User user) {
        Individual individual = individualMapper.map(individualNewDto).toBuilder()
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
    public Mono<IndividualDetailsDto> findById(UUID individualId) {
        return profileRepository.findById(individualId)
                .flatMap(this::findUserByProfile)
                .flatMap(this::findIndividualByUser)
                .map(individualMapper::mapIndividualDetailsDto);
    }

    private Mono<User> findUserByProfile(Profile profile) {
        log.info("Finding user by profile id: {}", profile.getId());
        return userRepository.findByProfileId(profile.getId())
                .doOnSuccess(foundUser ->
                        log.info("User found successfully {} by profile id: {}", foundUser, profile.getId()))
                .doOnError(throwable ->
                        log.error("Failed to find user by profile id: {} {}", throwable.getMessage(), profile.getId()))
                .map(foundUser -> foundUser.toBuilder()
                        .profile(profile)
                        .build());
    }

    private Mono<Individual> findIndividualByUser(User user) {
        log.info("Finding individual by user id: {}", user.getId());
        return individualRepository.findByUserId(user.getId())
                .doOnSuccess(foundIndividual ->
                        log.info("Individual found successfully {} by user id: {}", foundIndividual, user.getId()))
                .doOnError(throwable ->
                        log.error("Failed to find individual by user id: {} {}", throwable.getMessage(), user.getId()))
                .map(foundIndividual -> foundIndividual.toBuilder()
                        .user(user)
                        .build());
    }
}
