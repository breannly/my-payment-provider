package com.example.mypaymentprovider.api.individual.response;

import com.example.mypaymentprovider.api.individual.model.Individual;
import com.example.mypaymentprovider.api.individual.model.Status;

public class IndividualCreateResponse {

    public static IndividualCreateResponse success(Individual individual) {
        return new IndividualCreateResponse().setStatus(Status.SUCCESS).setIndividual(individual);
    }

    public static IndividualCreateResponse success(String description) {
        return new IndividualCreateResponse().setStatus(Status.SUCCESS).setDescription(description);
    }

    public static IndividualCreateResponse error(String description) {
        return new IndividualCreateResponse().setStatus(Status.ERROR).setDescription(description);
    }

    private Status status;
    private Individual individual;
    private String description;

    public Status getStatus() {
        return status;
    }

    public IndividualCreateResponse setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Individual getIndividual() {
        return individual;
    }

    public IndividualCreateResponse setIndividual(Individual individual) {
        this.individual = individual;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public IndividualCreateResponse setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return "IndividualCreateResponse{" +
                "status=" + status +
                ", individual=" + individual +
                ", description='" + description + '\'' +
                '}';
    }
}
