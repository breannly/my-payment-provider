package com.example.mygatewayuserservice.client.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

public class AuthIndividualClient {

    @Value("api.client.auth.url")
    private String url;
    @Value("api.client.auth.timeoutInSeconds:2")
    private int timeoutInSeconds;

    private final WebClient webClient;

    public AuthIndividualClient(WebClient webClient) {
        this.webClient = webClient;
    }


}
