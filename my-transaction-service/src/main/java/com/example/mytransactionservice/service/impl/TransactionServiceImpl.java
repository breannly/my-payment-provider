package com.example.mytransactionservice.service.impl;

import com.example.mytransactionservice.dto.transaction.TransactionDto;
import com.example.mytransactionservice.dto.transaction.TransactionNewDto;
import com.example.mytransactionservice.dto.transaction.TransactionShortDto;
import com.example.mytransactionservice.entity.transaction.TransactionStatus;
import com.example.mytransactionservice.entity.transaction.TransactionType;
import com.example.mytransactionservice.mapper.TransactionMapper;
import com.example.mytransactionservice.repository.TransactionRepository;
import com.example.mytransactionservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;

    @Override
    public Mono<TransactionShortDto> save(UUID merchantId, TransactionNewDto transactionNewDto) {
        return Mono.just(transactionMapper.map(transactionNewDto))
                .flatMap(transaction -> transactionRepository.save(transaction.toBuilder()
                        .merchantId(merchantId)
                        .transactionType(TransactionType.TRANSACTION)
                        .transactionStatus(TransactionStatus.IN_PROGRESS)
                        .build()))
                .map(transactionMapper::mapShort);
    }

    @Override
    public Mono<TransactionDto> findById(UUID merchantId, UUID uid) {
        return transactionRepository.findByIdAndMerchantId(uid, merchantId)
                .filter(transaction -> transaction.getTransactionType().equals(TransactionType.TRANSACTION))
                .map(transactionMapper::map);
    }
}
