package com.example.mygatewayuserservice.exception;

import org.springframework.http.HttpStatus;

public class AuthServiceException extends ApiException {

    public AuthServiceException(HttpStatus status, String message) {
        super(status, message);
    }
}
