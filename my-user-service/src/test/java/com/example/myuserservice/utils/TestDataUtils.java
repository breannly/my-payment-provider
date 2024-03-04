package com.example.myuserservice.utils;

import com.example.myuserservice.dto.IndividualNewDto;
import com.example.myuserservice.entity.Individual;
import org.jeasy.random.EasyRandom;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestDataUtils {

    private static final EasyRandom EASY_RANDOM = new EasyRandom();

    public static IndividualNewDto createIndividualNewDto() {
        IndividualNewDto individualNewDto = EASY_RANDOM.nextObject(IndividualNewDto.class);
        individualNewDto.setEmail("test@test.com");
        individualNewDto.setCreatedAt(LocalDateTime.now());
        individualNewDto.setDateOfBirth(LocalDate.now());
        return individualNewDto;
    }

    public static Individual createIndividual() {
        return EASY_RANDOM.nextObject(Individual.class);
    }
}
