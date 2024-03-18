package com.example.myuserservice.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Component
public class ErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

    public ErrorWebExceptionHandler(ErrorAttributes errorAttributes,
                                    ApplicationContext applicationContext,
                                    ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, new WebProperties.Resources(), applicationContext);
        setMessageWriters(serverCodecConfigurer.getWriters());
        setMessageReaders(serverCodecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(org.springframework.boot.web.reactive.error.ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        var errorAttributes = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        int httpStatus = (int) errorAttributes.getOrDefault("http_status", 500);
        String responseStatus = (String) errorAttributes.getOrDefault("response_status", "ERROR");
        String description = (String) errorAttributes.getOrDefault("description", "");

        return ServerResponse.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(Map.of("status", responseStatus, "description", description)))
                .doOnSuccess(response -> log.info("Response: status: {}, error: {}", httpStatus, description));
    }
}
