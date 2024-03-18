package com.example.myuserservice.exception;

import com.example.mypaymentprovider.api.individual.model.Status;
import org.springframework.http.HttpStatus;

public class IndividualServiceException extends ApiException {

    public IndividualServiceException(Status responseStatus, HttpStatus httpStatus, String message) {
        super(responseStatus, httpStatus, message);
    }
}
