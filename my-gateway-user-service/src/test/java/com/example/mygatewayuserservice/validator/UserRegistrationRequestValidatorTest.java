package com.example.mygatewayuserservice.validator;

import com.example.mygatewayuserservice.utils.TestDataUtils;
import com.example.mygatewayuserservice.dto.UserRegistrationRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserRegistrationRequestValidatorTest {

    @Test
    void should_return_isError_when_password_not_match_with_confirmPassword() {
        UserRegistrationRequest request = TestDataUtils.createUserRegistrationRequest();
        request.setConfirmPassword("confirm_password");

        ValidationResult result = UserRegistrationRequestValidator.validate(request);

        Assertions.assertTrue(result.isError());
        Assertions.assertFalse(result.isSuccess());
    }

    @Test
    void should_return_success_when_password_match_with_confirmPassword() {
        UserRegistrationRequest request = TestDataUtils.createUserRegistrationRequest();

        ValidationResult result = UserRegistrationRequestValidator.validate(request);

        Assertions.assertTrue(result.isSuccess());
        Assertions.assertFalse(result.isError());
    }

}