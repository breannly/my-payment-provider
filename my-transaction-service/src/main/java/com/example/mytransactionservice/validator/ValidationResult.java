package com.example.mytransactionservice.validator;

import lombok.Getter;

public class ValidationResult {

    @Getter
    private final Status status;
    private final String error;

    public ValidationResult(Status status, String error) {
        this.status = status;
        this.error = error;
    }

    public static ValidationResult success() {
        return new ValidationResult(Status.SUCCESS, "");
    }

    public static ValidationResult error(String error) {
        return new ValidationResult(Status.ERROR, error);
    }

    public boolean isSuccess() {
        return this.status == Status.SUCCESS;
    }

    public boolean isError() {
        return this.status == Status.ERROR;
    }

    public String getError() {
        return error;
    }

    public static enum Status {
        SUCCESS,
        ERROR;

        private Status() {

        }
    }
}
