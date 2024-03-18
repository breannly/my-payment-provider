package com.example.mypaymentprovider.api.individual.response;

import com.example.mypaymentprovider.api.individual.model.Individual;
import com.example.mypaymentprovider.api.individual.model.Status;

public class IndividualGetByIdResponse {

    public static IndividualGetByIdResponse success(Individual individual) {
        return new IndividualGetByIdResponse().setStatus(Status.SUCCESS).setIndividual(individual);
    }

    public static IndividualGetByIdResponse success(String description) {
        return new IndividualGetByIdResponse().setStatus(Status.SUCCESS).setDescription(description);
    }

    public static IndividualGetByIdResponse error(String description) {
        return new IndividualGetByIdResponse().setStatus(Status.ERROR).setDescription(description);
    }

    private Status status;
    private Individual individual;
    private String description;

    public Status getStatus() {
        return status;
    }

    public IndividualGetByIdResponse setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Individual getIndividual() {
        return individual;
    }

    public IndividualGetByIdResponse setIndividual(Individual individual) {
        this.individual = individual;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public IndividualGetByIdResponse setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return "IndividualGetByIdResponse{" +
                "status=" + status +
                ", individual=" + individual +
                ", description='" + description + '\'' +
                '}';
    }
}
