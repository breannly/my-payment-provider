package com.example.mytransactionservice.entity.transaction;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CardData {

    private String cardNumber;
    private String expDate;
    private String cvv;
}
