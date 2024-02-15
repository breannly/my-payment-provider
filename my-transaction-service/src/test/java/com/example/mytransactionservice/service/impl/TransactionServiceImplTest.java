package com.example.mytransactionservice.service.impl;

import com.example.mytransactionservice.dto.transaction.TransactionDto;
import com.example.mytransactionservice.dto.transaction.TransactionNewDto;
import com.example.mytransactionservice.dto.transaction.TransactionShortDto;
import com.example.mytransactionservice.entity.transaction.Transaction;
import com.example.mytransactionservice.entity.transaction.TransactionStatus;
import com.example.mytransactionservice.entity.transaction.TransactionType;
import com.example.mytransactionservice.mapper.TransactionMapper;
import com.example.mytransactionservice.repository.TransactionRepository;
import com.example.mytransactionservice.service.TransactionService;
import com.example.mytransactionservice.utils.TestDataUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

public class TransactionServiceImplTest {

    private TransactionMapper mapper;
    private TransactionRepository transactionRepository;
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(TransactionMapper.class);
        transactionRepository = Mockito.mock(TransactionRepository.class);
        transactionService = new TransactionServiceImpl(mapper, transactionRepository);
    }

    @Test
    public void test_save() {
        UUID merchantId = UUID.randomUUID();
        TransactionNewDto transactionNewDto = TestDataUtils.createTransactionNewDto();
        Transaction transaction = mapper.map(transactionNewDto);
        TransactionShortDto transactionShortDto = mapper.mapShort(transaction);

        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(Mono.just(transaction));

        StepVerifier.create(transactionService.save(merchantId, transactionNewDto))
                .expectNext(transactionShortDto)
                .verifyComplete();

        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        Mockito.verify(transactionRepository).save(transactionCaptor.capture());
        Transaction capturedTransaction = transactionCaptor.getValue();

        Assertions.assertEquals(capturedTransaction.getMerchantId(), merchantId);
        Assertions.assertEquals(capturedTransaction.getTransactionType(), TransactionType.TRANSACTION);
        Assertions.assertEquals(capturedTransaction.getTransactionStatus(), TransactionStatus.IN_PROGRESS);
    }

    @Test
    public void should_return_transaction_when_type_is_transaction() {
        UUID merchantId = UUID.randomUUID();
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = TestDataUtils.createTransaction().toBuilder()
                .id(transactionId)
                .merchantId(merchantId)
                .transactionType(TransactionType.TRANSACTION)
                .build();
        TransactionDto transactionDto = mapper.map(transaction);

        Mockito.when(transactionRepository.findByIdAndMerchantId(transactionId, merchantId))
                .thenReturn(Mono.just(transaction));

        StepVerifier.create(transactionService.findById(merchantId, transactionId))
                .expectNext(transactionDto)
                .verifyComplete();

        Mockito.verify(transactionRepository).findByIdAndMerchantId(transactionId, merchantId);
    }

    @Test
    public void should_not_return_transaction_when_type_is_not_transaction() {
        UUID merchantId = UUID.randomUUID();
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = TestDataUtils.createTransaction().toBuilder()
                .id(transactionId)
                .merchantId(merchantId)
                .transactionType(TransactionType.PAYOUT)
                .build();

        Mockito.when(transactionRepository.findByIdAndMerchantId(transactionId, merchantId))
                .thenReturn(Mono.just(transaction));

        StepVerifier.create(transactionService.findById(merchantId, transactionId))
                .verifyComplete();

        Mockito.verify(transactionRepository).findByIdAndMerchantId(transactionId, merchantId);
    }
}