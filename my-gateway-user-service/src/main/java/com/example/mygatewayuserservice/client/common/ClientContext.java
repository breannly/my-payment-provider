package com.example.mygatewayuserservice.client;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WebClientContext {

    private final String url;
    private final Long timeoutInSeconds;
}
