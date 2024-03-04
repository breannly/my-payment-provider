package com.example.myuserservice.repository;

import com.example.myuserservice.entity.history.ProfileHistory;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ProfileHistoryRepository extends R2dbcRepository<ProfileHistory, Long> {
}
