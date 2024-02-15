package com.example.mytransactionservice.mapper;

import com.example.mytransactionservice.dto.payout.PayoutDto;
import com.example.mytransactionservice.dto.payout.PayoutNewDto;
import com.example.mytransactionservice.dto.payout.PayoutShortDto;
import com.example.mytransactionservice.entity.transaction.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PayoutMapper {

    Transaction map(PayoutNewDto payoutNewDto);

    @Mapping(target = "externalTransactionId", source = "id")
    @Mapping(target = "status", source = "transactionStatus")
    PayoutShortDto mapShort(Transaction transaction);

    @Mapping(target = "externalTransactionId", source = "id")
    @Mapping(target = "status", source = "transactionStatus")
    PayoutDto map(Transaction transaction);
}
