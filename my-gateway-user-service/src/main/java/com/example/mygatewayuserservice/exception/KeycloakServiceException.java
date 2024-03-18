package com.example.mygatewayuserservice.exception;

import org.springframework.http.HttpStatus;

public class KeycloakServiceException extends ApiException {

    public KeycloakServiceException(HttpStatus status, String message) {
        super(status, message);
    }
}
