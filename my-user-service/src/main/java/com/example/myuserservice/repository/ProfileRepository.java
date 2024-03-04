package com.example.myuserservice.repository;

import com.example.myuserservice.entity.profile.Profile;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface ProfileRepository extends R2dbcRepository<Profile, UUID> {
}
