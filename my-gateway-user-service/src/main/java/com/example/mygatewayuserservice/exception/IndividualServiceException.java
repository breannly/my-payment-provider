package com.example.mygatewayuserservice.exception;

import org.springframework.http.HttpStatus;

public class IndividualServiceException extends ApiException {

    public IndividualServiceException(HttpStatus status, String message) {
        super(status, message);
    }
}
