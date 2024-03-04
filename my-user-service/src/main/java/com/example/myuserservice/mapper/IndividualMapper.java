package com.example.myuserservice.mapper;

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

    @Mapping(target = "user.uid", source = "user.profile.id")
    @Mapping(target = "user.username", source = "user.profile.username")
    @Mapping(target = "user.isPasswordSet", source = "user.profile.isPasswordSet")
    @Mapping(target = "user.status", source = "user.status")
    @Mapping(target = "personalInfo.firstName", source = "user.firstName")
    @Mapping(target = "personalInfo.lastName", source = "user.lastName")
    @Mapping(target = "personalInfo.dateOfBirth", source = "dateOfBirth")
    @Mapping(target = "contactInfo.email", source = "user.email")
    @Mapping(target = "contactInfo.phoneNumber", source = "user.phoneNumber")
    @Mapping(target = "createdAt", source = "createdAt")
    IndividualDto map(Individual individual);
}
