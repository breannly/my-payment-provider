package com.example.mytransactionservice.manager.processor;

import com.example.mytransactionservice.entity.transaction.Transaction;
import reactor.core.publisher.Mono;

public interface PayoutProcessor {

    Mono<Transaction> process(Transaction transaction);
}
