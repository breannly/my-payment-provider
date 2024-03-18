package com.example.mypaymentprovider.api.individual.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Individual {

    private UUID uid;
    private String username;
    private Contact contact;
    private Name name;
    private PersonalDetails personalDetails;
    private Status status;
    private String applicantId;
    private String language;
    private String profileType;
    private boolean filled;
    private Security security;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public PersonalDetails getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProfileType() {
        return profileType;
    }

    public void setProfileType(String profileType) {
        this.profileType = profileType;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "IndividualDetailsResponse{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", contact=" + contact +
                ", name=" + name +
                ", personalDetails=" + personalDetails +
                ", status=" + status +
                ", applicantId='" + applicantId + '\'' +
                ", language='" + language + '\'' +
                ", profileType='" + profileType + '\'' +
                ", filled=" + filled +
                ", security=" + security +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }

    public static class Contact {
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
            return "Contact{" +
                    "email='" + email + '\'' +
                    ", phoneNumber='" + phoneNumber + '\'' +
                    '}';
        }
    }

    public static class Name {
        private String firstName;
        private String secondName;
        private String fullName;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getSecondName() {
            return secondName;
        }

        public void setSecondName(String secondName) {
            this.secondName = secondName;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        @Override
        public String toString() {
            return "Name{" +
                    "firstName='" + firstName + '\'' +
                    ", secondName='" + secondName + '\'' +
                    ", fullName='" + fullName + '\'' +
                    '}';
        }
    }

    public static class PersonalDetails {
        private String dateOfBirth;
        private String passportNumber;
        private String personalIdentityNumber;
        private String gender;

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getPassportNumber() {
            return passportNumber;
        }

        public void setPassportNumber(String passportNumber) {
            this.passportNumber = passportNumber;
        }

        public String getPersonalIdentityNumber() {
            return personalIdentityNumber;
        }

        public void setPersonalIdentityNumber(String personalIdentityNumber) {
            this.personalIdentityNumber = personalIdentityNumber;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        @Override
        public String toString() {
            return "PersonalDetails{" +
                    "dateOfBirth='" + dateOfBirth + '\'' +
                    ", passportNumber='" + passportNumber + '\'' +
                    ", personalIdentityNumber='" + personalIdentityNumber + '\'' +
                    ", gender='" + gender + '\'' +
                    '}';
        }
    }

    public static class Status {
        private String current;
        private LocalDateTime verifiedAt;
        private LocalDateTime archivedAt;

        public String getCurrent() {
            return current;
        }

        public void setCurrent(String current) {
            this.current = current;
        }

        public LocalDateTime getVerifiedAt() {
            return verifiedAt;
        }

        public void setVerifiedAt(LocalDateTime verifiedAt) {
            this.verifiedAt = verifiedAt;
        }

        public LocalDateTime getArchivedAt() {
            return archivedAt;
        }

        public void setArchivedAt(LocalDateTime archivedAt) {
            this.archivedAt = archivedAt;
        }

        @Override
        public String toString() {
            return "Status{" +
                    "current='" + current + '\'' +
                    ", verifiedAt='" + verifiedAt + '\'' +
                    ", archivedAt='" + archivedAt + '\'' +
                    '}';
        }
    }

    public static class Security {
        private boolean enabled;
        private boolean emailVerified;
        private boolean preferMobile2fa;
        private boolean isAdmin;
        private boolean isPasswordSet;
        private boolean isSocial;
        private boolean is2faEnabled;
        private String password;
        private String secretKey;
        private String lastProfileUid;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public boolean isEmailVerified() {
            return emailVerified;
        }

        public void setEmailVerified(boolean emailVerified) {
            this.emailVerified = emailVerified;
        }

        public boolean isPreferMobile2fa() {
            return preferMobile2fa;
        }

        public void setPreferMobile2fa(boolean preferMobile2fa) {
            this.preferMobile2fa = preferMobile2fa;
        }

        public boolean getIsAdmin() {
            return isAdmin;
        }

        public void setIsAdmin(boolean isAdmin) {
            this.isAdmin = isAdmin;
        }

        public boolean getIsPasswordSet() {
            return isPasswordSet;
        }

        public void setIsPasswordSet(boolean isPasswordSet) {
            this.isPasswordSet = isPasswordSet;
        }

        public boolean getIsSocial() {
            return isSocial;
        }

        public void setIsSocial(boolean isSocial) {
            this.isSocial = isSocial;
        }

        public boolean getIs2faEnabled() {
            return is2faEnabled;
        }

        public void setIs2faEnabled(boolean is2faEnabled) {
            this.is2faEnabled = is2faEnabled;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public String getLastProfileUid() {
            return lastProfileUid;
        }

        public void setLastProfileUid(String lastProfileUid) {
            this.lastProfileUid = lastProfileUid;
        }

        @Override
        public String toString() {
            return "Security{" +
                    "enabled=" + enabled +
                    ", emailVerified=" + emailVerified +
                    ", preferMobile2fa=" + preferMobile2fa +
                    ", isAdmin=" + isAdmin +
                    ", isPasswordSet=" + isPasswordSet +
                    ", isSocial=" + isSocial +
                    ", is2faEnabled=" + is2faEnabled +
                    ", password='" + password + '\'' +
                    ", secretKey='" + secretKey + '\'' +
                    ", lastProfileUid='" + lastProfileUid + '\'' +
                    '}';
        }
    }
}
