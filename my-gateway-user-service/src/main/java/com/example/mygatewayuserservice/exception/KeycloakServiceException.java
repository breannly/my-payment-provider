package com.example.mygatewayuserservice.exception;

public class KeycloakServiceException extends ApiException {

    public KeycloakServiceException(ErrorStatus status, String message) {
        super(status, message);
    }
}
