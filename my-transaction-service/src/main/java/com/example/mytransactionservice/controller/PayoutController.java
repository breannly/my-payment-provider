package com.example.mytransactionservice.controller;

import com.example.mytransactionservice.dto.payout.PayoutDto;
import com.example.mytransactionservice.dto.payout.PayoutNewDto;
import com.example.mytransactionservice.dto.payout.PayoutShortDto;
import com.example.mytransactionservice.service.PayoutService;
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
@RequestMapping("/api/v1/payments/payout")
public class PayoutController {

    private final AuthorizationValidator authorizationValidator;
    private final PayoutService payoutService;

    @PostMapping
    public Mono<ResponseEntity<PayoutShortDto>> save(@RequestHeader("Authorization") String authHeader,
                                                     @RequestBody @Validated PayoutNewDto payoutNewDto) {
        return authorizationValidator.validate(authHeader)
                .flatMap(validationResult -> {
                    if (validationResult.isError()) {
                        log.error("Save Payout - Authorization Error: {}", validationResult.getError());
                        return Mono.just(new ResponseEntity<>(HttpStatus.FORBIDDEN));
                    }
                    UUID merchantId = CredentialsUtils.getIdFromHeader(authHeader);
                    log.info("Merchant ID: {} - Save Payout: {}", merchantId, payoutNewDto);
                    return payoutService.save(merchantId, payoutNewDto)
                            .doOnSuccess(response -> log.info("Merchant ID: {} - Saved Payout: {}", merchantId, response))
                            .doOnError(throwable -> log.error("Merchant ID: {} - Save Payout Error: {}", merchantId, throwable.getMessage()))
                            .map(payout -> new ResponseEntity<>(payout, HttpStatus.OK));
                });
    }

    @GetMapping("/{payoutId}/details")
    public Mono<ResponseEntity<PayoutDto>> findById(@RequestHeader("Authorization") String authHeader,
                                                    @PathVariable UUID payoutId) {
        return authorizationValidator.validate(authHeader)
                .flatMap(validationResult -> {
                    if (validationResult.isError()) {
                        log.error("Retrieve Payout - Authorization Error: {}", validationResult.getError());
                        return Mono.just(new ResponseEntity<>(HttpStatus.FORBIDDEN));
                    }
                    UUID merchantId = CredentialsUtils.getIdFromHeader(authHeader);
                    log.info("Merchant ID: {} - Retrieve Payout ID: {}", merchantId, payoutId);
                    return payoutService.findById(merchantId, payoutId)
                            .doOnSuccess(response -> log.info("Merchant ID: {} - Retrieved Payout: {}", merchantId, response))
                            .doOnError(throwable -> log.error("Merchant ID: {} - Retrieve Payout Error: {}", merchantId, throwable.getMessage()))
                            .map(payout -> new ResponseEntity<>(payout, HttpStatus.OK));
                });
    }
}
