package com.example.mytransactionservice.utils;

import com.example.mytransactionservice.dto.payout.PayoutNewDto;
import com.example.mytransactionservice.dto.transaction.TransactionNewDto;
import com.example.mytransactionservice.entity.transaction.Transaction;
import org.jeasy.random.EasyRandom;

public class TestDataUtils {

    private static final EasyRandom EASY_RANDOM = new EasyRandom();

    public static Transaction createTransaction() {
        return EASY_RANDOM.nextObject(Transaction.class);
    }

    public static TransactionNewDto createTransactionNewDto() {
        return EASY_RANDOM.nextObject(TransactionNewDto.class);
    }

    public static PayoutNewDto createPayoutNewDto() {
        return EASY_RANDOM.nextObject(PayoutNewDto.class);
    }
}
