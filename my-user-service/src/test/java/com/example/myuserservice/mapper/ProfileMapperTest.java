//package com.example.myuserservice.mapper;
//
//import com.example.myuserservice.dto.IndividualNewDto;
//import com.example.myuserservice.entity.profile.Profile;
//import com.example.myuserservice.utils.TestDataUtils;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mapstruct.factory.Mappers;
//
//class ProfileMapperTest {
//
//    private final ProfileMapper mapper = Mappers.getMapper(ProfileMapper.class);
//
//    @Test
//    public void should_map_profile_from_individualNewDto() {
//        IndividualNewDto individualNewDto = TestDataUtils.createIndividualNewDto();
//
//        Profile profile = mapper.map(individualNewDto);
//
//        Assertions.assertEquals(individualNewDto.getCreatedAt(), profile.getCreatedAt());
//        Assertions.assertEquals(individualNewDto.getCreatedAt(), profile.getUpdatedAt());
//        Assertions.assertEquals(individualNewDto.getUsername(), profile.getUsername());
//        Assertions.assertEquals(individualNewDto.getPassword(), profile.getPassword());
//        Assertions.assertEquals(individualNewDto.getSecretKey(), profile.getSecretKey());
//    }
//}