package com.example.myuserservice.mapper;

import com.example.myuserservice.dto.IndividualNewDto;
import com.example.myuserservice.entity.profile.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfileMapper {

    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "createdAt")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "secretKey", source = "secretKey")
    Profile map(IndividualNewDto individualNewDto);
}
