package com.example.mygatewayuserservice.validator;

import com.example.mygatewayuserservice.dto.UserRegistrationRequest;

public class UserRegistrationRequestValidator {

    public static ValidationResult validate(UserRegistrationRequest request) {
        if (request.getUsername() == null && request.getEmail() == null) {
            return ValidationResult.error("Both username and email are missing. At least one is required to register the user.");
        }
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return ValidationResult.error("Password and confirm password do not match.");
        }
        return ValidationResult.success();
    }
}
