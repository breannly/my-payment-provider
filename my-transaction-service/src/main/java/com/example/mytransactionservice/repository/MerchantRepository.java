package com.example.mytransactionservice.repository;

import com.example.mytransactionservice.entity.Merchant;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface MerchantRepository extends R2dbcRepository<Merchant, UUID> {
}
