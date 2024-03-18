package com.example.mygatewayuserservice.mapper;

import com.example.mygatewayuserservice.dto.UserLoginResponse;
import com.example.mygatewayuserservice.dto.UserRegistrationResponse;
import org.keycloak.representations.AccessTokenResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthMapper {

    @Mapping(target = "accessToken", source = "token")
    UserRegistrationResponse mapUserRegistrationResponse(AccessTokenResponse accessTokenResponse);

    @Mapping(target = "accessToken", source = "token")
    UserLoginResponse mapUserLoginResponse(AccessTokenResponse accessTokenResponse);
}
