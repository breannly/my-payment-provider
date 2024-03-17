package com.example.mygatewayuserservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class UserGetMeResponse {

    private String username;
    private Name name;
    private Contact contact;
    private String status;
    private LocalDateTime updatedAt;

    @Getter
    @Setter
    @ToString
    public static class Contact {

        private String email;
        private String phoneNumber;
    }

    @Getter
    @Setter
    @ToString
    public static class Name {

        private String firstName;
        private String secondName;
        private String fullName;
    }

}
