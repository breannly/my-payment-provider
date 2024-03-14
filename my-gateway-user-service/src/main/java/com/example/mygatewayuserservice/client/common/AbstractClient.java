package com.example.mygatewayuserservice.client;

import org.slf4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

public abstract class AbstractClient {

    private final WebClient webClient;
    private final WebClientContext context;

    protected AbstractClient(WebClient webClient, String url, Long timeOutInSecond) {
        this.webClient = webClient;
        this.context = WebClientContext.builder()
                .url(url)
                .timeoutInSeconds(timeOutInSecond)
                .build();
    }

    public <REQ, RES> Mono<RES> execute(HttpMethod httpMethod, REQ request, String path, Class<RES> responseClazz) {
        WebClient.RequestHeadersSpec<?> requestSpec = webClient
                .method(httpMethod)
                .uri(path)
                .bodyValue(request);
        return sendAndReceive(requestSpec, responseClazz);
    }

    private <RES> Mono<RES> sendAndReceive(WebClient.RequestHeadersSpec<?> requestSpec, Class<RES> responseClazz) {
        return requestSpec.retrieve()
                .onStatus(HttpStatusCode::isError, this::handleErrorResponse)
                .bodyToMono(responseClazz)
                .timeout(Duration.ofSeconds(context.getTimeoutInSeconds()))
                .onErrorResume(this::handleErrorResume);
    }

    private <RES> Mono<RES> handleErrorResume(Throwable throwable) {
        log().error("Unexpected exception: {}", throwable.getMessage());
        return Mono.error(new RuntimeException(throwable.getMessage()));
    }

    private Mono<? extends Throwable> handleErrorResponse(ClientResponse response) {
        return response.bodyToMono(String.class)
                .flatMap(body -> {
                    log().error("Not allowed status code: {}, message: {}", response.statusCode(), body);
                    return Mono.error(new RuntimeException());
                });
    }

    protected abstract Logger log();
}
