package com.example.mytransactionservice.repository;

import com.example.mytransactionservice.entity.Wallet;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface WalletRepository extends R2dbcRepository<Wallet, UUID> {

    Mono<Wallet> findWalletByMerchantIdAndCurrency(UUID merchantId, String currency);
}
