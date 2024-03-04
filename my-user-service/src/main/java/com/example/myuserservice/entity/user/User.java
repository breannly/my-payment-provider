package com.example.myuserservice.entity.user;

import com.example.myuserservice.entity.profile.Profile;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("person.users")
public class User {

    @Id
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Column(value = "profile_uid")
    private UUID profileId;
    @Transient
    private Profile profile;
    private UserStatus status;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Boolean filled;
}
