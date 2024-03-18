package com.example.myuserservice.exception;

import com.example.mypaymentprovider.api.individual.model.Status;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    private final Status responseStatus;
    private final HttpStatus httpStatus;

    public ApiException(Status responseStatus, HttpStatus httpStatus, String message) {
        super(message);
        this.responseStatus = responseStatus;
        this.httpStatus = httpStatus;
    }
}
