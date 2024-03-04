package com.example.myuserservice.repository;

import com.example.myuserservice.entity.Individual;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface IndividualRepository extends R2dbcRepository<Individual, Long> {
}
