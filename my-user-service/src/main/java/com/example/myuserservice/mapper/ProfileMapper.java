package com.example.myuserservice.mapper;

import com.example.mypaymentprovider.api.individual.request.IndividualCreateRequest;
import com.example.myuserservice.entity.profile.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfileMapper {

    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "createdAt")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    Profile map(IndividualCreateRequest individualCreateRequest);
}
