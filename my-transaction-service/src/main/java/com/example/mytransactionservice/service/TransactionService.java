package com.example.mytransactionservice.service;

import com.example.mytransactionservice.dto.transaction.TransactionDto;
import com.example.mytransactionservice.dto.transaction.TransactionNewDto;
import com.example.mytransactionservice.dto.transaction.TransactionShortDto;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TransactionService {

    Mono<TransactionShortDto> save(UUID merchantId, TransactionNewDto transactionNewDto);

    Mono<TransactionDto> findById(UUID merchantId, UUID uid);
}
