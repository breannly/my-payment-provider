package com.example.mytransactionservice.dto.payout;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PayoutDto {

    private UUID externalTransactionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String paymentMethod;
    private BigDecimal amount;
    private String currency;
    private String notificationUrl;
    private CardDataDto cardData;
    private String language;
    private CustomerDto customer;
    private String status;
    private String message;
}
