package com.example.mygatewayuserservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    private final HttpStatus status;
    private final String message;

    public ApiException(ErrorStatus status, String message) {
        super();
        this.status = status.getHttpStatus();
        this.message = message;
    }
}
