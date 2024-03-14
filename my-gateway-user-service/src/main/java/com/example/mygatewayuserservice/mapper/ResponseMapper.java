package com.example.mygatewayuserservice.mapper;

import com.example.mygatewayuserservice.dto.UserRegistrationResponse;
import org.keycloak.representations.AccessTokenResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResponseMapper {

    UserRegistrationResponse map(AccessTokenResponse response);
}
