package com.example.mytransactionservice.validator;

import com.example.mytransactionservice.repository.MerchantRepository;
import com.example.mytransactionservice.utils.CredentialsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthorizationValidator {

    private final MerchantRepository merchantRepository;

    public Mono<ValidationResult> validate(String authHeader) {
        if (!isValidAuthHeader(authHeader)) {
            return Mono.just(ValidationResult.error("INVALID_HEADER"));
        }

        try {
            Pair<UUID, String> credentials = CredentialsUtils.getCredentialsFromHeader(authHeader);
            return validateCredentials(credentials);
        } catch (IllegalArgumentException e) {
            return Mono.just(ValidationResult.error("INVALID_CREDENTIALS"));
        }

    }

    private boolean isValidAuthHeader(String authHeader) {
        return authHeader != null && authHeader.startsWith("Basic ");
    }

    private Mono<ValidationResult> validateCredentials(Pair<UUID, String> credentials) {
        return merchantRepository.findById(credentials.getFirst())
                .map(merchant -> merchant.getSecretKey().equals(credentials.getSecond()) ?
                        ValidationResult.success() :
                        ValidationResult.error("INCORRECT_CREDENTIALS"))
                .defaultIfEmpty(ValidationResult.error("MERCHANT_NOT_FOUND"));
    }
}
