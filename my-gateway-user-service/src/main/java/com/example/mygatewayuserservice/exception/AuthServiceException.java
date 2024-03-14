package com.example.mygatewayuserservice.exception;

public class AuthServiceException extends ApiException {

    public AuthServiceException(ErrorStatus status, String message) {
        super(status, message);
    }
}
