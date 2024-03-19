package com.example.mygatewayuserservice.utils;

import com.example.mygatewayuserservice.dto.UserRegistrationRequest;
import com.example.mypaymentprovider.api.individual.model.Individual;
import org.jeasy.random.EasyRandom;

public class TestDataUtils {

    private static final EasyRandom EASY_RANDOM = new EasyRandom();

    public static UserRegistrationRequest createUserRegistrationRequest() {
        UserRegistrationRequest userRegistrationRequest = EASY_RANDOM.nextObject(UserRegistrationRequest.class);
        userRegistrationRequest.setEmail(userRegistrationRequest.getEmail() + "@test.com");
        userRegistrationRequest.setConfirmPassword(userRegistrationRequest.getPassword());
        return userRegistrationRequest;
    }

    public static Individual createIndividual() {
        return EASY_RANDOM.nextObject(Individual.class);
    }
}
