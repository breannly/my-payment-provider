package com.example.mytransactionservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
@Table("transaction.wallets")
public class Wallet {

    @Id
    @Column(value = "uid")
    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Column(value = "merchant_uid")
    private UUID merchantId;
    @Transient
    private Merchant merchant;
    private String currency;
    private BigDecimal balance;
}
