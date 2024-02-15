package com.example.mytransactionservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("transaction.merchants")
public class Merchant {

    @Id
    @Column(value = "uid")
    private UUID id;
    private String secretKey;
}
