package com.example.mytransactionservice.mapper;

import com.example.mytransactionservice.dto.transaction.TransactionDto;
import com.example.mytransactionservice.dto.transaction.TransactionNewDto;
import com.example.mytransactionservice.dto.transaction.TransactionShortDto;
import com.example.mytransactionservice.entity.transaction.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {

    Transaction map(TransactionNewDto transactionNewDto);

    @Mapping(target = "externalTransactionId", source = "id")
    @Mapping(target = "status", source = "transactionStatus")
    TransactionShortDto mapShort(Transaction transaction);

    @Mapping(target = "externalTransactionId", source = "id")
    @Mapping(target = "status", source = "transactionStatus")
    TransactionDto map(Transaction transaction);
}
