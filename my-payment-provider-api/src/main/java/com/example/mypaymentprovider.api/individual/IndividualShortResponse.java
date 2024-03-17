package com.example.mypaymentprovider.api.individual;

import java.util.UUID;

public class IndividualShortResponse {

    private UUID id;
    private String status;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "IndividualShortDto{" +
                "id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}
