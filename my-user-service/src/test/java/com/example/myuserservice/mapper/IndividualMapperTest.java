package com.example.myuserservice.mapper;

import com.example.myuserservice.dto.IndividualNewDto;
import com.example.myuserservice.entity.Individual;
import com.example.myuserservice.entity.profile.Profile;
import com.example.myuserservice.entity.user.User;
import com.example.myuserservice.utils.TestDataUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class IndividualMapperTest {

    private final IndividualMapper mapper = Mappers.getMapper(IndividualMapper.class);

    @Test
    public void should_map_individual_from_individualNewDto() {
        IndividualNewDto individualNewDto = TestDataUtils.createIndividualNewDto();

        Individual individual = mapper.map(individualNewDto);

        Assertions.assertEquals(individualNewDto.getCreatedAt(), individual.getCreatedAt());
        Assertions.assertEquals(individualNewDto.getCreatedAt(), individual.getUpdatedAt());
        Assertions.assertEquals(individualNewDto.getDateOfBirth(), individual.getDateOfBirth());
    }

    @Test
    public void should_map_individualDto_from_individual() {
        Individual individual = TestDataUtils.createIndividual();
        User user = individual.getUser();
        Profile profile = individual.getUser().getProfile();

//        IndividualDto individualDto = mapper.map(individual);

//        Assertions.assertEquals(profile.getId(), individualDto.getUser().getUid());
//        Assertions.assertEquals(profile.getUsername(), individualDto.getUser().getUsername());
//        Assertions.assertEquals(profile.getIsPasswordSet(), individualDto.getUser().getIsPasswordSet());
//        Assertions.assertEquals(user.getStatus().name(), individualDto.getUser().getStatus());
//        Assertions.assertEquals(user.getFirstName(), individualDto.getPersonalInfo().getFirstName());
//        Assertions.assertEquals(user.getLastName(), individualDto.getPersonalInfo().getLastName());
//        Assertions.assertEquals(individual.getDateOfBirth(), individualDto.getPersonalInfo().getDateOfBirth());
//        Assertions.assertEquals(user.getEmail(), individualDto.getContactInfo().getEmail());
//        Assertions.assertEquals(user.getPhoneNumber(), individualDto.getContactInfo().getPhoneNumber());
    }
}