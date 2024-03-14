package com.example.mygatewayuserservice.client.common;

import com.example.mygatewayuserservice.utils.JacksonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

public abstract class AbstractClient {

    private static final ObjectMapper OBJECT_MAPPER = JacksonUtils.generateCustomObjectMapper();

    private final WebClient webClient;
    private final ClientContext context;

    protected AbstractClient(String url, Long timeOutInSecond) {
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> {
                    configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(OBJECT_MAPPER));
                    configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(OBJECT_MAPPER ));
                })
                .build();
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .exchangeStrategies(exchangeStrategies)
                .build();
        this.context = ClientContext.builder()
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

    public <REQ, RES> Mono<RES> execute(HttpMethod httpMethod, String path, Class<RES> responseClazz) {
        WebClient.RequestHeadersSpec<?> requestSpec = webClient
                .method(httpMethod)
                .uri(path);
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
