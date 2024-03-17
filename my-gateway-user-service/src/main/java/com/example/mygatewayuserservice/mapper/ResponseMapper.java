package com.example.mygatewayuserservice.mapper;

import com.example.mygatewayuserservice.dto.UserGetMeResponse;
import com.example.mygatewayuserservice.dto.UserLoginResponse;
import com.example.mygatewayuserservice.dto.UserRegistrationResponse;
import com.example.mypaymentprovider.api.individual.IndividualDetailsResponse;
import org.keycloak.representations.AccessTokenResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResponseMapper {

    @Mapping(target = "accessToken", source = "token")
    UserRegistrationResponse mapUserRegistrationResponse(AccessTokenResponse response);

    @Mapping(target = "accessToken", source = "token")
    UserLoginResponse mapUserLoginResponse(AccessTokenResponse response);

    @Mapping(target = "status", source = "status.current")
    UserGetMeResponse mapUserGetMeResponse(IndividualDetailsResponse response);
}
