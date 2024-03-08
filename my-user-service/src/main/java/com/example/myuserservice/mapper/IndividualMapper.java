package com.example.myuserservice.mapper;

import com.example.myuserservice.dto.IndividualDetailsDto;
import com.example.myuserservice.dto.IndividualDto;
import com.example.myuserservice.dto.IndividualNewDto;
import com.example.myuserservice.entity.Individual;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IndividualMapper {

    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "createdAt")
    @Mapping(target = "dateOfBirth", source = "dateOfBirth")
    Individual map(IndividualNewDto individualNewDto);

    @Mapping(target = "uid", source = "user.profile.id")
    @Mapping(target = "status", source = "user.status")
    IndividualDto mapIndividualDto(Individual individual);

    @Mapping(target = "username", source = "user.profile.username")
    @Mapping(target = "isPasswordSet", source = "user.profile.isPasswordSet")
    @Mapping(target = "status", source = "user.status")
    @Mapping(target = "personalInfo.firstName", source = "user.firstName")
    @Mapping(target = "personalInfo.lastName", source = "user.lastName")
    @Mapping(target = "personalInfo.dateOfBirth", source = "dateOfBirth")
    @Mapping(target = "contactInfo.email", source = "user.email")
    @Mapping(target = "contactInfo.phoneNumber", source = "user.phoneNumber")
    @Mapping(target = "updatedAt", source = "createdAt")
    IndividualDetailsDto mapIndividualDetailsDto(Individual individual);
}
