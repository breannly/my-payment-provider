package com.example.mytransactionservice.manager;

import com.example.mytransactionservice.entity.transaction.TransactionStatus;
import com.example.mytransactionservice.entity.transaction.TransactionType;
import com.example.mytransactionservice.manager.processor.PayoutProcessor;
import com.example.mytransactionservice.manager.processor.TransactionProcessor;
import com.example.mytransactionservice.manager.publisher.WebhookPublisher;
import com.example.mytransactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionManagerImpl implements TransactionManager {

    private final TransactionRepository transactionRepository;
    private final TransactionProcessor transactionProcessor;
    private final PayoutProcessor payoutProcessor;
    private final WebhookPublisher webhookPublisher;

    @Override
    @Scheduled(fixedDelay = 5000)
    public void manageTransactions() {
        log.debug("Starting transaction processing");
        transactionRepository.findAllByTransactionTypeAndTransactionStatus(TransactionType.TRANSACTION, TransactionStatus.IN_PROGRESS)
                .flatMap(transaction -> {
                    log.info("Processing transaction: {}", transaction.getId());
                    return transactionProcessor.process(transaction)
                            .doOnSuccess(processedTransaction -> {
                                log.info("Transaction {} is processed successfully", processedTransaction.getId());
                            });
                })
                .flatMap(webhookPublisher::publish)
                .doOnError(error -> log.error("Error processing transactions: {}", error.getMessage()))
                .doOnComplete(() -> log.debug("Completed transaction processing"))
                .subscribe();
    }

    @Override
    @Scheduled(fixedDelay = 3000)
    public void managePayouts() {
        log.debug("Starting payout processing");
        transactionRepository.findAllByTransactionTypeAndTransactionStatus(TransactionType.PAYOUT, TransactionStatus.IN_PROGRESS)
                .flatMap(payout -> {
                    log.info("Processing payout: {}", payout.getId());
                    return payoutProcessor.process(payout)
                            .doOnSuccess(processedPayout -> {
                                log.info("Payout {} is processed successfully", processedPayout.getId());
                            });
                })
                .flatMap(webhookPublisher::publish)
                .doOnError(error -> log.error("Error processing payouts: {}", error.getMessage()))
                .doOnComplete(() -> log.debug("Completed payout processing"))
                .subscribe();
    }
}
