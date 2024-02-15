package com.example.mytransactionservice.dto.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransactionNewDto {

    @NotNull
    @PastOrPresent
    private LocalDateTime createdAt;
    @PastOrPresent
    private LocalDateTime updatedAt;
    @NotNull
    private String paymentMethod;
    @NotNull
    @Min(value = 0)
    private BigDecimal amount;
    @NotNull
    private String currency;
    @NotNull
    private CardDataDto cardData;
    @NotNull
    private String language;
    @NotNull
    private String notificationUrl;
    private CustomerDto customer;
}
