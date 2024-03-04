package com.example.myuserservice.mapper;

import com.example.myuserservice.dto.IndividualNewDto;
import com.example.myuserservice.entity.user.User;
import com.example.myuserservice.utils.TestDataUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    public void should_map_user_from_individualNewDto() {
        IndividualNewDto individualNewDto = TestDataUtils.createIndividualNewDto();

        User user = mapper.map(individualNewDto);

        Assertions.assertEquals(individualNewDto.getCreatedAt(), user.getCreatedAt());
        Assertions.assertEquals(individualNewDto.getCreatedAt(), user.getUpdatedAt());
        Assertions.assertEquals(individualNewDto.getFirstName(), user.getFirstName());
        Assertions.assertEquals(individualNewDto.getLastName(), user.getLastName());
        Assertions.assertEquals(individualNewDto.getEmail(), user.getEmail());
        Assertions.assertEquals(individualNewDto.getPhoneNumber(), user.getPhoneNumber());
    }
}