package com.example.mytransactionservice.service.impl;

import com.example.mytransactionservice.dto.payout.PayoutDto;
import com.example.mytransactionservice.dto.payout.PayoutNewDto;
import com.example.mytransactionservice.dto.payout.PayoutShortDto;
import com.example.mytransactionservice.entity.transaction.Transaction;
import com.example.mytransactionservice.entity.transaction.TransactionStatus;
import com.example.mytransactionservice.entity.transaction.TransactionType;
import com.example.mytransactionservice.mapper.PayoutMapper;
import com.example.mytransactionservice.repository.TransactionRepository;
import com.example.mytransactionservice.service.PayoutService;
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

public class PayoutServiceImplTest {

    private PayoutMapper mapper;
    private TransactionRepository transactionRepository;
    private PayoutService transactionService;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(PayoutMapper.class);
        transactionRepository = Mockito.mock(TransactionRepository.class);
        transactionService = new PayoutServiceImpl(mapper, transactionRepository);
    }

    @Test
    public void test_save() {
        UUID merchantId = UUID.randomUUID();
        PayoutNewDto payoutNewDto = TestDataUtils.createPayoutNewDto();
        Transaction transaction = mapper.map(payoutNewDto);
        PayoutShortDto payoutShortDto = mapper.mapShort(transaction);

        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(Mono.just(transaction));

        StepVerifier.create(transactionService.save(merchantId, payoutNewDto))
                .expectNext(payoutShortDto)
                .verifyComplete();

        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        Mockito.verify(transactionRepository).save(transactionCaptor.capture());
        Transaction capturedTransaction = transactionCaptor.getValue();

        Assertions.assertEquals(capturedTransaction.getMerchantId(), merchantId);
        Assertions.assertEquals(capturedTransaction.getTransactionType(), TransactionType.PAYOUT);
        Assertions.assertEquals(capturedTransaction.getTransactionStatus(), TransactionStatus.IN_PROGRESS);
    }

    @Test
    public void should_return_transaction_when_type_is_payout() {
        UUID merchantId = UUID.randomUUID();
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = TestDataUtils.createTransaction().toBuilder()
                .id(transactionId)
                .merchantId(merchantId)
                .transactionType(TransactionType.PAYOUT)
                .build();
        PayoutDto payoutDto = mapper.map(transaction);

        Mockito.when(transactionRepository.findByIdAndMerchantId(transactionId, merchantId))
                .thenReturn(Mono.just(transaction));

        StepVerifier.create(transactionService.findById(merchantId, transactionId))
                .expectNext(payoutDto)
                .verifyComplete();

        Mockito.verify(transactionRepository).findByIdAndMerchantId(transactionId, merchantId);
    }

    @Test
    public void should_not_return_transaction_when_type_is_not_payout() {
        UUID merchantId = UUID.randomUUID();
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = TestDataUtils.createTransaction().toBuilder()
                .id(transactionId)
                .merchantId(merchantId)
                .transactionType(TransactionType.TRANSACTION)
                .build();

        Mockito.when(transactionRepository.findByIdAndMerchantId(transactionId, merchantId))
                .thenReturn(Mono.just(transaction));

        StepVerifier.create(transactionService.findById(merchantId, transactionId))
                .verifyComplete();

        Mockito.verify(transactionRepository).findByIdAndMerchantId(transactionId, merchantId);
    }

}