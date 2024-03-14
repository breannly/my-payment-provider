package com.example.mygatewayuserservice.mapper;

import com.example.mygatewayuserservice.dto.UserRegistrationRequest;
import com.example.mypaymentprovider.api.individual.IndividualNewDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    IndividualNewDto map(UserRegistrationRequest source);
}
