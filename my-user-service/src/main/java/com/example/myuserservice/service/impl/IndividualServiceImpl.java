package com.example.myuserservice.service.impl;

import com.example.myuserservice.dto.IndividualDto;
import com.example.myuserservice.dto.IndividualNewDto;
import com.example.myuserservice.entity.Individual;
import com.example.myuserservice.entity.user.User;
import com.example.myuserservice.entity.history.ProfileHistory;
import com.example.myuserservice.entity.profile.Profile;
import com.example.myuserservice.entity.profile.ProfileType;
import com.example.myuserservice.entity.user.UserStatus;
import com.example.myuserservice.mapper.ChangedValuesMapper;
import com.example.myuserservice.mapper.IndividualMapper;
import com.example.myuserservice.mapper.ProfileMapper;
import com.example.myuserservice.mapper.UserMapper;
import com.example.myuserservice.repository.IndividualRepository;
import com.example.myuserservice.repository.ProfileHistoryRepository;
import com.example.myuserservice.repository.ProfileRepository;
import com.example.myuserservice.repository.UserRepository;
import com.example.myuserservice.service.IndividualService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndividualServiceImpl implements IndividualService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final IndividualRepository individualRepository;
    private final ProfileHistoryRepository profileHistoryRepository;
    private final ProfileMapper profileMapper;
    private final UserMapper userMapper;
    private final IndividualMapper individualMapper;
    private final ChangedValuesMapper changedValueMapper;

    @Override
    @Transactional
    public Mono<IndividualDto> register(IndividualNewDto individualNewDto) {
        return createProfile(individualNewDto)
                .flatMap(savedProfile -> createUser(individualNewDto, savedProfile))
                .flatMap(savedUser -> createIndividual(individualNewDto, savedUser))
                .flatMap(savedIndividual -> createProfileHistory(individualNewDto, savedIndividual))
                .map(individualMapper::map);
    }

    private Mono<Profile> createProfile(IndividualNewDto individualNewDto) {
        Profile profile = profileMapper.map(individualNewDto)
                .toBuilder()
                .profileType(ProfileType.INDIVIDUAL)
                .isPasswordSet(Objects.nonNull(individualNewDto.getPassword()))
                .build();

        log.info("Creating profile for individual: {}", profile);
        return profileRepository.save(profile)
                .doOnSuccess(savedProfile -> log.info("Profile saved successfully: {}", savedProfile))
                .doOnError(throwable -> log.error("Failed to create profile for individual: {}", throwable.getMessage()));
    }

    private Mono<User> createUser(IndividualNewDto individualNewDto, Profile savedProfile) {
        User user = userMapper.map(individualNewDto).toBuilder()
                .profileId(savedProfile.getId())
                .profile(savedProfile)
                .status(UserStatus.IS_PENDING)
                .filled(individualNewDto.getEmail() != null || individualNewDto.getPhoneNumber() != null)
                .build();

        log.info("Creating user for individual: {}", user);
        return userRepository.save(user)
                .doOnSuccess(savedUser -> log.info("User saved successfully: {}", savedUser))
                .doOnError(throwable -> log.error("Failed to create user for individual: {}", throwable.getMessage()));
    }

    private Mono<Individual> createIndividual(IndividualNewDto individualNewDto, User savedUser) {
        Individual individual = individualMapper.map(individualNewDto).toBuilder()
                .userId(savedUser.getId())
                .user(savedUser)
                .build();

        log.info("Creating individual for individual: {}", individual);
        return individualRepository.save(individual)
                .doOnSuccess(savedIndividual -> log.info("Individual saved successfully: {}", savedIndividual))
                .doOnError(throwable -> log.error("Failed to create individual for individual: {}", throwable.getMessage()));
    }

    private Mono<Individual> createProfileHistory(IndividualNewDto individualNewDto, Individual savedIndividual) {
        ProfileHistory profileHistory = ProfileHistory.builder()
                .profileId(savedIndividual.getUser().getProfileId())
                .userId(savedIndividual.getUserId())
                .createdAt(savedIndividual.getUser().getCreatedAt())
                .changedValues(changedValueMapper.map(individualNewDto))
                .build();
        return profileHistoryRepository.save(profileHistory)
                .thenReturn(savedIndividual);
    }
}
