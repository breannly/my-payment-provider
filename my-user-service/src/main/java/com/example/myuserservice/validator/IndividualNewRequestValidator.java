package com.example.myuserservice.validator;

import com.example.mypaymentprovider.api.individual.request.IndividualCreateRequest;

import java.util.ArrayList;
import java.util.List;

public class IndividualNewRequestValidator {

    public static ValidationResult validate(IndividualCreateRequest request) {
        List<String> missingFields = new ArrayList<>();
        if (request.getUsername() == null) {
            missingFields.add("username");
        }
        if (request.getEmail() == null) {
            missingFields.add("email");
        }
        if (request.getPassword() == null) {
            missingFields.add("password");
        }
        if (request.getFirstName() == null) {
            missingFields.add("firstName");
        }
        if (request.getSecondName() == null) {
            missingFields.add("secondName");
        }

        if (!missingFields.isEmpty()) {
            return ValidationResult.error("Required fields missing: " + String.join(", ", missingFields));
        }

        return ValidationResult.success();
    }
}
