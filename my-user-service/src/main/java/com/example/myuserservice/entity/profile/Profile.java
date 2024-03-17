package com.example.myuserservice.entity.profile;

import lombok.*;
import org.springframework.data.annotation.Id;
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
@Table("person.profiles")
public class Profile {

    @Id
    @Column(value = "uid")
    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String username;
    private String password;
    private String secretKey;
    private Boolean enabled;
    private Boolean isAdmin;
    private ProfileType profileType;
    private String language;
    private Boolean emailVerified;
//    private Boolean preferMobile2Fa;
    private Boolean isPasswordSet;
    private Boolean isSocial;
//    private Boolean is2FaEnabled;
}
