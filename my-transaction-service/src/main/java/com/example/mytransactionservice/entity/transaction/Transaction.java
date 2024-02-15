package com.example.mytransactionservice.entity.transaction;

import com.example.mytransactionservice.entity.Merchant;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("transaction.transactions")
public class Transaction {

    @Id
    @Column(value = "uid")
    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Column(value = "merchant_uid")
    private UUID merchantId;
    @Transient
    private Merchant merchant;
    @Column(value = "type")
    private TransactionType transactionType;
    private String paymentMethod;
    private BigDecimal amount;
    private String currency;
    private CardData cardData;
    private String language;
    private String notificationUrl;
    private Customer customer;
    @Column(value = "status")
    private TransactionStatus transactionStatus;
    private String message;
}
