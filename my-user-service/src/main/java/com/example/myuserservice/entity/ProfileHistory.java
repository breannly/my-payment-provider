package com.example.myuserservice.entity;

import com.example.myuserservice.entity.profile.Profile;
import com.example.myuserservice.entity.user.User;
import io.r2dbc.postgresql.codec.Json;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("person.profile_history")
public class ProfileHistory {

    @Id
    private Long id;
    private LocalDateTime createdAt;
    @Column(value = "target_profile_uid")
    private UUID profileId;
    @Transient
    private Profile profile;
    @Column(value = "changed_by_user_uid")
    private Long userId;
    @Transient
    private User user;
    private String reason;
    private String comment;
    private Json changedValues;

    @Override
    public String toString() {
        return "ProfileHistory{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", profileId=" + profileId +
                ", userId=" + userId +
                ", reason='" + reason + '\'' +
                ", comment='" + comment + '\'' +
                ", changedValues=" + changedValues +
                '}';
    }
}
