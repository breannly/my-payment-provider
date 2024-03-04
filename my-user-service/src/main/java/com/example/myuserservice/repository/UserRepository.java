package com.example.myuserservice.repository;

import com.example.myuserservice.entity.user.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UserRepository extends R2dbcRepository<User, Long> {
}
