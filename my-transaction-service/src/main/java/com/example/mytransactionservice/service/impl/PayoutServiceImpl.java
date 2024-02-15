package com.example.mytransactionservice.service.impl;

import com.example.mytransactionservice.dto.payout.PayoutDto;
import com.example.mytransactionservice.dto.payout.PayoutNewDto;
import com.example.mytransactionservice.dto.payout.PayoutShortDto;
import com.example.mytransactionservice.entity.transaction.TransactionStatus;
import com.example.mytransactionservice.entity.transaction.TransactionType;
import com.example.mytransactionservice.mapper.PayoutMapper;
import com.example.mytransactionservice.repository.TransactionRepository;
import com.example.mytransactionservice.service.PayoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PayoutServiceImpl implements PayoutService {

    private final PayoutMapper payoutMapper;
    private final TransactionRepository transactionRepository;

    @Override
    public Mono<PayoutShortDto> save(UUID merchantId, PayoutNewDto payoutNewDto) {
        return Mono.just(payoutMapper.map(payoutNewDto))
                .flatMap(transaction -> transactionRepository.save(transaction.toBuilder()
                        .merchantId(merchantId)
                        .transactionType(TransactionType.PAYOUT)
                        .transactionStatus(TransactionStatus.IN_PROGRESS)
                        .build()))
                .map(payoutMapper::mapShort);
    }

    @Override
    public Mono<PayoutDto> findById(UUID merchantId, UUID uid) {
        return transactionRepository.findByIdAndMerchantId(uid, merchantId)
                .filter(transaction -> transaction.getTransactionType().equals(TransactionType.PAYOUT))
                .map(payoutMapper::map);
    }
}
