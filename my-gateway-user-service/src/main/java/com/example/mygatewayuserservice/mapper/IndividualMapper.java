package com.example.mygatewayuserservice.mapper;

import com.example.mygatewayuserservice.dto.UserGetMeResponse;
import com.example.mygatewayuserservice.dto.UserRegistrationRequest;
import com.example.mypaymentprovider.api.individual.model.Individual;
import com.example.mypaymentprovider.api.individual.request.IndividualCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IndividualMapper {

    IndividualCreateRequest map(UserRegistrationRequest source);

    @Mapping(target = "status", source = "status.current")
    UserGetMeResponse map(Individual individual);
}
