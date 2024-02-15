package com.example.mytransactionservice.repository;

import com.example.mytransactionservice.entity.Webhook;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface WebhookRepository extends R2dbcRepository<Webhook, UUID> {
}
