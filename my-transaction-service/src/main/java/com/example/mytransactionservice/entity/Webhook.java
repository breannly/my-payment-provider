package com.example.mytransactionservice.entity;

import com.example.mytransactionservice.entity.transaction.Transaction;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("transaction.webhooks")
public class Webhook {

    @Id
    @Column(value = "uid")
    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Column(value = "transaction_uid")
    private UUID transactionId;
    @Column(value = "transaction_data")
    private Transaction transaction;
    private int count;
    private String statusCode;
    private String message;
}
