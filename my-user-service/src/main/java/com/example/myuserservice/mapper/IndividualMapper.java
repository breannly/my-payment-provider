package com.example.myuserservice.mapper;

import com.example.mypaymentprovider.api.individual.IndividualDetailsResponse;
import com.example.mypaymentprovider.api.individual.IndividualNewRequest;
import com.example.mypaymentprovider.api.individual.IndividualShortResponse;
import com.example.myuserservice.entity.Individual;
import com.example.myuserservice.entity.user.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IndividualMapper {

    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "createdAt")
    Individual map(IndividualNewRequest individualNewRequest);

    @Mapping(target = "id", source = "user.profile.id")
    @Mapping(target = "status", source = "user.status")
    IndividualShortResponse map(Individual individual);

    @Mapping(target = "uid", source = "user.profile.id")
    @Mapping(target = "username", source = "user.profile.username")
    @Mapping(target = "contact.email", source = "user.email")
    @Mapping(target = "contact.phoneNumber", source = "user.phoneNumber")
    @Mapping(target = "name", source = "user")
    @Mapping(target = "personalDetails.dateOfBirth", source = "dateOfBirth")
    @Mapping(target = "personalDetails.passportNumber", source = "passportNumber")
    @Mapping(target = "personalDetails.gender", source = "gender")
    @Mapping(target = "status.current", source = "user.status")
    @Mapping(target = "language", source = "user.profile.language")
    @Mapping(target = "profileType", source = "user.profile.profileType")
    @Mapping(target = "filled", source = "user.filled")
    @Mapping(target = "security.enabled", source = "user.profile.enabled")
    @Mapping(target = "security.emailVerified", source = "user.profile.emailVerified")
    @Mapping(target = "security.isAdmin", source = "user.profile.isAdmin")
    @Mapping(target = "security.isPasswordSet", source = "user.profile.isPasswordSet")
    @Mapping(target = "security.isSocial", source = "user.profile.isSocial")
    @Mapping(target = "createdAt", source = "user.profile.createdAt")
    @Mapping(target = "updatedAt", source = "user.profile.updatedAt")
    IndividualDetailsResponse mapToIndividualDetailsResponse(Individual individual);

    default IndividualDetailsResponse.Name mapName(User user) {
        if (user == null) {
            return null;
        }
        IndividualDetailsResponse.Name name = new IndividualDetailsResponse.Name();
        name.setFirstName(user.getFirstName());
        name.setSecondName(user.getLastName());
        name.setFullName(user.getFirstName() + " " + user.getLastName());
        return name;
    }
}
