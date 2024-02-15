package com.example.mytransactionservice.controller;

import com.example.mytransactionservice.dto.transaction.TransactionDto;
import com.example.mytransactionservice.dto.transaction.TransactionNewDto;
import com.example.mytransactionservice.dto.transaction.TransactionShortDto;
import com.example.mytransactionservice.service.TransactionService;
import com.example.mytransactionservice.utils.CredentialsUtils;
import com.example.mytransactionservice.validator.AuthorizationValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments/transaction")
public class TransactionController {

    private final AuthorizationValidator authorizationValidator;
    private final TransactionService transactionService;

    @PostMapping
    public Mono<ResponseEntity<TransactionShortDto>> save(@RequestHeader("Authorization") String authHeader,
                                                          @RequestBody @Validated TransactionNewDto transactionNewDto) {
        return authorizationValidator.validate(authHeader)
                .flatMap(validationResult -> {
                    if (validationResult.isError()) {
                        log.error("Save Transaction - Authorization Error: {}", validationResult.getError());
                        return Mono.just(new ResponseEntity<>(HttpStatus.FORBIDDEN));
                    }
                    UUID merchantId = CredentialsUtils.getIdFromHeader(authHeader);
                    log.info("Merchant ID: {} - Save Transaction: {}", merchantId, transactionNewDto);
                    return transactionService.save(merchantId, transactionNewDto)
                            .doOnSuccess(response -> log.info("Merchant ID: {} - Saved Transaction: {}", merchantId, response))
                            .doOnError(throwable -> log.error("Merchant ID: {} - Save Transaction Error: {}", merchantId, throwable.getMessage()))
                            .map(payout -> new ResponseEntity<>(payout, HttpStatus.OK));
                });
    }

    @GetMapping("/{transactionId}/details")
    public Mono<ResponseEntity<TransactionDto>> findById(@RequestHeader("Authorization") String authHeader,
                                                        @PathVariable UUID transactionId) {
        return authorizationValidator.validate(authHeader)
                .flatMap(validationResult -> {
                    if (validationResult.isError()) {
                        log.error("Retrieve Transaction - Authorization Error: {}", validationResult.getError());
                        return Mono.just(new ResponseEntity<>(HttpStatus.FORBIDDEN));
                    }
                    UUID merchantId = CredentialsUtils.getIdFromHeader(authHeader);
                    log.info("Merchant ID: {} - Retrieve Transaction ID: {}", merchantId, transactionId);
                    return transactionService.findById(merchantId, transactionId)
                            .doOnSuccess(response -> log.info("Merchant ID: {} - Retrieved Transaction: {}", merchantId, response))
                            .doOnError(throwable -> log.error("Merchant ID: {} - Retrieve Transaction Error: {}", merchantId, throwable.getMessage()))
                            .map(payout -> new ResponseEntity<>(payout, HttpStatus.OK));
                });
    }
}
