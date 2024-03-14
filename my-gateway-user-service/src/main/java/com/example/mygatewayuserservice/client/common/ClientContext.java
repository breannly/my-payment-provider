package com.example.mygatewayuserservice.client.common;

import lombok.*;

@Getter
@Builder
public class ClientContext {

    private final String url;
    private final Long timeoutInSeconds;
}
