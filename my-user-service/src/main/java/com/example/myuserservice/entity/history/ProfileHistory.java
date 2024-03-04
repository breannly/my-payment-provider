package com.example.myuserservice.entity.history;

import com.example.myuserservice.entity.profile.Profile;
import com.example.myuserservice.entity.user.User;
import lombok.*;
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
@Table("person.profile_history")
public class ProfileHistory {

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
    private ChangedValues changedValues;
}
