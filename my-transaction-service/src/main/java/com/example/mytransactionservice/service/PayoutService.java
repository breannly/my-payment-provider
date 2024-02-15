package com.example.mytransactionservice.service;

import com.example.mytransactionservice.dto.payout.PayoutDto;
import com.example.mytransactionservice.dto.payout.PayoutNewDto;
import com.example.mytransactionservice.dto.payout.PayoutShortDto;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PayoutService {

    Mono<PayoutShortDto> save(UUID merchantId, PayoutNewDto payoutNewDto);

    Mono<PayoutDto> findById(UUID merchantId, UUID uid);
}
