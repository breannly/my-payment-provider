package com.example.mytransactionservice.mapper;

import com.example.mytransactionservice.dto.transaction.TransactionDto;
import com.example.mytransactionservice.dto.transaction.TransactionNewDto;
import com.example.mytransactionservice.dto.transaction.TransactionShortDto;
import com.example.mytransactionservice.entity.transaction.Transaction;
import com.example.mytransactionservice.utils.JsonUtils;
import com.example.mytransactionservice.utils.TestDataUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class TransactionMapperTest {

    private final TransactionMapper MAPPER = Mappers.getMapper(TransactionMapper.class);

    @Test
    public void should_map_transaction_from_transactionNewDto() {
        TransactionNewDto transactionNewDto = TestDataUtils.createTransactionNewDto();

        Transaction result = MAPPER.map(transactionNewDto);

        asserts(transactionNewDto, result);
    }

    private void asserts(TransactionNewDto expected, Transaction actual) {
        Assertions.assertEquals(expected.getCreatedAt(), actual.getCreatedAt());
        Assertions.assertEquals(expected.getUpdatedAt(), actual.getUpdatedAt());
        Assertions.assertEquals(expected.getPaymentMethod(), actual.getPaymentMethod());
        Assertions.assertEquals(expected.getAmount(), actual.getAmount());
        Assertions.assertEquals(expected.getCurrency(), actual.getCurrency());
        Assertions.assertEquals(expected.getCardData().getCardNumber(), actual.getCardData().getCardNumber());
        Assertions.assertEquals(expected.getCardData().getCvv(), actual.getCardData().getCvv());
        Assertions.assertEquals(expected.getCardData().getExpDate(), actual.getCardData().getExpDate());
        Assertions.assertEquals(expected.getNotificationUrl(), actual.getNotificationUrl());
        Assertions.assertEquals(expected.getCustomer().getCountry(), actual.getCustomer().getCountry());
        Assertions.assertEquals(expected.getCustomer().getFirstName(), actual.getCustomer().getFirstName());
        Assertions.assertEquals(expected.getCustomer().getLastName(), actual.getCustomer().getLastName());
        Assertions.assertEquals(expected.getLanguage(), actual.getLanguage());
    }

    @Test
    public void should_map_transactionShortDto_from_transaction() {
        Transaction transaction = TestDataUtils.createTransaction();

        TransactionShortDto result = MAPPER.mapShort(transaction);

        asserts(transaction, result);
    }

    private void asserts(Transaction expected, TransactionShortDto actual) {
        Assertions.assertEquals(expected.getId(), actual.getExternalTransactionId());
        Assertions.assertEquals(expected.getCreatedAt(), actual.getCreatedAt());
        Assertions.assertEquals(expected.getUpdatedAt(), actual.getUpdatedAt());
        Assertions.assertEquals(expected.getTransactionStatus().name(), actual.getStatus());
        Assertions.assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    public void should_map_transactionDto_from_transaction() {
        Transaction transaction = TestDataUtils.createTransaction();

        TransactionDto result = MAPPER.map(transaction);

        asserts(transaction, result);
    }

    private void asserts(Transaction expected, TransactionDto actual) {
        Assertions.assertEquals(expected.getId(), actual.getExternalTransactionId());
        Assertions.assertEquals(expected.getCreatedAt(), actual.getCreatedAt());
        Assertions.assertEquals(expected.getUpdatedAt(), actual.getUpdatedAt());
        Assertions.assertEquals(expected.getPaymentMethod(), actual.getPaymentMethod());
        Assertions.assertEquals(expected.getAmount(), actual.getAmount());
        Assertions.assertEquals(expected.getCurrency(), actual.getCurrency());
        Assertions.assertEquals(expected.getCardData().getCardNumber(), actual.getCardData().getCardNumber());
        Assertions.assertEquals(expected.getCardData().getCvv(), actual.getCardData().getCvv());
        Assertions.assertEquals(expected.getCardData().getExpDate(), actual.getCardData().getExpDate());
        Assertions.assertEquals(expected.getNotificationUrl(), actual.getNotificationUrl());
        Assertions.assertEquals(expected.getCustomer().getCountry(), actual.getCustomer().getCountry());
        Assertions.assertEquals(expected.getCustomer().getFirstName(), actual.getCustomer().getFirstName());
        Assertions.assertEquals(expected.getCustomer().getLastName(), actual.getCustomer().getLastName());
        Assertions.assertEquals(expected.getLanguage(), actual.getLanguage());
        Assertions.assertEquals(expected.getTransactionStatus().name(), actual.getStatus());
        Assertions.assertEquals(expected.getMessage(), actual.getMessage());
    }

}