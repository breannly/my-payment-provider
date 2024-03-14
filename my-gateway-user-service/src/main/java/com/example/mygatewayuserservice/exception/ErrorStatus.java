package com.example.mygatewayuserservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorStatus {
    
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST),
    KEYCLOAK_FAILED(HttpStatus.CONFLICT),
    ALREADY_EXISTS(HttpStatus.CONFLICT);
    
    private final HttpStatus httpStatus;

    ErrorStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}
