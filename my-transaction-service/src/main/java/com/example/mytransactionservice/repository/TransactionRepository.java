package com.example.mytransactionservice.repository;

import com.example.mytransactionservice.entity.transaction.Transaction;
import com.example.mytransactionservice.entity.transaction.TransactionStatus;
import com.example.mytransactionservice.entity.transaction.TransactionType;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TransactionRepository extends R2dbcRepository<Transaction, UUID> {

    Flux<Transaction> findAllByMerchantIdAndCreatedAtBetween(UUID merchantUid, LocalDateTime startDate, LocalDateTime endDate);

    Mono<Transaction> findByIdAndMerchantId(UUID id, UUID merchantId);

    Flux<Transaction> findAllByTransactionTypeAndTransactionStatus(TransactionType transactionType, TransactionStatus status);
}
