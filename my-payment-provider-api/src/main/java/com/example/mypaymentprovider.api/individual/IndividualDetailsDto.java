package com.example.mypaymentprovider.api.individual;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class IndividualDetailsDto {

    private String username;
    private Boolean isPasswordSet;
    private String status;
    private PersonalInfo personalInfo;
    private ContactInfo contactInfo;
    private LocalDateTime updatedAt;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getPasswordSet() {
        return isPasswordSet;
    }

    public void setIsPasswordSet(Boolean passwordSet) {
        isPasswordSet = passwordSet;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static class PersonalInfo {

        private String firstName;
        private String lastName;
        private LocalDate dateOfBirth;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public LocalDate getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        @Override
        public String toString() {
            return "PersonalInfo{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", dateOfBirth=" + dateOfBirth +
                    '}';
        }
    }

    public static class ContactInfo {

        private String email;
        private String phoneNumber;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        @Override
        public String toString() {
            return "ContactInfo{" +
                    "email='" + email + '\'' +
                    ", phoneNumber='" + phoneNumber + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "IndividualDetailsDto{" +
                "username='" + username + '\'' +
                ", isPasswordSet=" + isPasswordSet +
                ", status='" + status + '\'' +
                ", personalInfo=" + personalInfo +
                ", contactInfo=" + contactInfo +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
