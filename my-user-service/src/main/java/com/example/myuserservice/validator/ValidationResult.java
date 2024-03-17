package com.example.myuserservice.validator;

import reactor.core.publisher.Mono;

public record ValidationResult(Status status, String error) {

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

    public Mono<ValidationResult> toMono() {
        return Mono.just(this);
    }

    public static enum Status {
        SUCCESS,
        ERROR;

        private Status() {

        }
    }
}
