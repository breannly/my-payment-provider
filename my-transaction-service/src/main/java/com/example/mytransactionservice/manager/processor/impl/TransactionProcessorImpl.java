package com.example.mytransactionservice.manager.processor.impl;

import com.example.mytransactionservice.entity.Wallet;
import com.example.mytransactionservice.entity.transaction.Transaction;
import com.example.mytransactionservice.entity.transaction.TransactionStatus;
import com.example.mytransactionservice.manager.processor.TransactionProcessor;
import com.example.mytransactionservice.repository.TransactionRepository;
import com.example.mytransactionservice.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TransactionProcessorImpl implements TransactionProcessor {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;

    private static final String[] FAILED_TRANSACTION_MESSAGES = {
            "PAYMENT_METHOD_NOT_ALLOWED",
            "INSUFFICIENT_FUNDS",
            "CARD_EXPIRED",
            "NETWORK_ERROR",
            "INVALID_CREDENTIALS",
            "UNAUTHORIZED_ACCESS",
            "CURRENCY_MISMATCH",
            "LIMIT_EXCEEDED",
            "TRANSACTION_DECLINED",
            "SERVICE_UNAVAILABLE"
    };

    @Override
    public Mono<Transaction> process(Transaction transaction) {
        return walletRepository.findWalletByMerchantIdAndCurrency(transaction.getMerchantId(), transaction.getCurrency())
                .defaultIfEmpty(Wallet.builder()
                        .merchantId(transaction.getMerchantId())
                        .currency(transaction.getCurrency())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build())
                .flatMap(wallet -> processTransaction(transaction, wallet))
                .flatMap(transactionRepository::save);
    }

    private Mono<Transaction> processTransaction(Transaction transaction, Wallet wallet) {
        transaction.setTransactionStatus(generateTransactionStatus());
        if (transaction.getTransactionStatus().equals(TransactionStatus.APPROVED)) {
            return processCompletedTransaction(transaction, wallet);
        } else {
            return Mono.just(processFailedTransaction(transaction, generateFailedMessage()));
        }
    }

    private Mono<Transaction> processCompletedTransaction(Transaction transaction, Wallet wallet) {
        if (transaction.getAmount().compareTo(BigDecimal.ZERO) >= 0) {
            wallet.setBalance(Optional.ofNullable(wallet.getBalance()).orElse(BigDecimal.ZERO).add(transaction.getAmount()));
            wallet.setUpdatedAt(LocalDateTime.now());
            transaction.setUpdatedAt(LocalDateTime.now());
            transaction.setMessage("OK");
            return walletRepository.save(wallet)
                    .thenReturn(transaction);
        } else {
            return Mono.just(processFailedTransaction(transaction, "INSUFFICIENT_BALANCE"));
        }
    }

    private Transaction processFailedTransaction(Transaction transaction, String message) {
        transaction.setUpdatedAt(LocalDateTime.now());
        transaction.setMessage(message);
        return transaction;
    }

    private TransactionStatus generateTransactionStatus() {
        return Math.random() < 0.90 ? TransactionStatus.APPROVED : TransactionStatus.FAILED;
    }

    private String generateFailedMessage() {
        int randomIndex = (int) (Math.random() * FAILED_TRANSACTION_MESSAGES.length);
        return FAILED_TRANSACTION_MESSAGES[randomIndex];
    }
}
