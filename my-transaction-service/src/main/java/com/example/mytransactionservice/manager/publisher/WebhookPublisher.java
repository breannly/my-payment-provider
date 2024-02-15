package com.example.mytransactionservice.manager.publisher;

import com.example.mytransactionservice.entity.Webhook;
import com.example.mytransactionservice.entity.transaction.Transaction;
import reactor.core.publisher.Mono;

public interface WebhookPublisher {

    Mono<Webhook> publish(Transaction transaction);
}
