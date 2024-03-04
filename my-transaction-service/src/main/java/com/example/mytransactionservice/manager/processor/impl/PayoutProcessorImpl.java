package com.example.mytransactionservice.manager.processor.impl;

import com.example.mytransactionservice.entity.Wallet;
import com.example.mytransactionservice.entity.transaction.Transaction;
import com.example.mytransactionservice.entity.transaction.TransactionStatus;
import com.example.mytransactionservice.manager.processor.PayoutProcessor;
import com.example.mytransactionservice.repository.TransactionRepository;
import com.example.mytransactionservice.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PayoutProcessorImpl implements PayoutProcessor {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;

    private static final String[] FAILED_PAYOUT_MESSAGES = {
            "BENEFICIARY_ACCOUNT_INVALID",
            "PAYOUT_AMOUNT_EXCEEDS_LIMIT",
            "BENEFICIARY_ACCOUNT_FROZEN",
            "CURRENCY_CONVERSION_NOT_AVAILABLE",
            "PAYOUT_METHOD_UNAVAILABLE",
            "NETWORK_ISSUE",
            "AUTHENTICATION_FAILED",
            "UNAUTHORIZED_OPERATION",
            "SERVICE_UNAVAILABLE_FOR_PAYOUT"
    };

    @Override
    @Transactional
    public Mono<Transaction> process(Transaction payout) {
        return walletRepository.findWalletByMerchantIdAndCurrency(payout.getMerchantId(), payout.getCurrency())
                .flatMap(wallet -> processPayout(payout, wallet))
                .defaultIfEmpty(processFailedPayout(payout, "WALLET_NOT_FOUND"))
                .flatMap(transactionRepository::save);
    }

    private Mono<Transaction> processPayout(Transaction payout, Wallet wallet) {
        TransactionStatus transactionStatus = generateTransactionStatus();
        if (transactionStatus.equals(TransactionStatus.APPROVED)) {
            return processCompletedPayout(payout, wallet);
        } else {
            return Mono.just(processFailedPayout(payout, generateFailedMessage()));
        }
    }

    private Mono<Transaction> processCompletedPayout(Transaction payout, Wallet wallet) {
        if (wallet.getBalance().compareTo(payout.getAmount()) >= 0) {
            wallet.setBalance(wallet.getBalance().subtract(payout.getAmount()));
            wallet.setUpdatedAt(LocalDateTime.now());
            payout.setUpdatedAt(LocalDateTime.now());
            payout.setMessage("OK");
            return walletRepository.save(wallet).thenReturn(payout);
        } else {
            return Mono.just(processFailedPayout(payout, "INSUFFICIENT_BALANCE"));
        }
    }

    private Transaction processFailedPayout(Transaction transaction, String message) {
        transaction.setUpdatedAt(LocalDateTime.now());
        transaction.setTransactionStatus(TransactionStatus.FAILED);
        transaction.setMessage(message);
        return transaction;
    }

    private TransactionStatus generateTransactionStatus() {
        return Math.random() < 0.90 ? TransactionStatus.APPROVED : TransactionStatus.FAILED;
    }

    private String generateFailedMessage() {
        int randomIndex = (int) (Math.random() * FAILED_PAYOUT_MESSAGES.length);
        return FAILED_PAYOUT_MESSAGES[randomIndex];
    }
}
