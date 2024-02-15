package com.example.mytransactionservice.manager.publisher;

import com.example.mytransactionservice.entity.Webhook;
import com.example.mytransactionservice.entity.transaction.Transaction;
import com.example.mytransactionservice.repository.WebhookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebhookPublisherImpl implements WebhookPublisher {

    private final WebhookRepository webhookRepository;
    private final WebClient webClient;

    @Override
    public Mono<Webhook> publish(Transaction transaction) {
        Webhook webhook = Webhook.builder()
                .transactionId(transaction.getId())
                .transaction(transaction)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return publish(transaction.getNotificationUrl(), webhook);
    }

    private Mono<Webhook> publish(String url, Webhook webhook) {
        return webClient.post()
                .uri(url)
                .bodyValue(webhook)
                .exchangeToMono(clientResponse -> processResponse(clientResponse, webhook))
                .retryWhen(Retry.backoff(3, Duration.ofMillis(50))
                        .doAfterRetry(retrySignal -> updateWebhookAfterRetry(webhook))
                        .onRetryExhaustedThrow(((retryBackoffSpec, retrySignal) ->
                                new RuntimeException("Send webhook failed after max retries")
                        )))
                .flatMap(response -> webhookRepository.save(webhook))
                .onErrorResume(throwable -> {
                            log.error("Error processing webhook: {}", throwable.getMessage());
                            return Mono.just(webhook);
                        }
                );
    }

    private Mono<ClientResponse> processResponse(ClientResponse clientResponse, Webhook webhook) {
        return clientResponse.bodyToMono(String.class)
                .map(body -> {
                    webhook.setStatusCode(clientResponse.statusCode().toString());
                    webhook.setMessage(body.substring(0, Math.min(body.length(), 256)));
                    return clientResponse;
                })
                .flatMap(response -> {
                    if (response.statusCode().isError()) {
                        return Mono.error(new RuntimeException("Webhook response status error"));
                    }
                    return Mono.just(response);
                });
    }

    private void updateWebhookAfterRetry(Webhook webhook) {
        saveWebhookAfterRetry(webhook).subscribe(savedWebhook -> {
            webhook.setCount(savedWebhook.getCount());
        });
    }

    private Mono<Webhook> saveWebhookAfterRetry(Webhook webhook) {
        int attempt = webhook.getCount() + 1;
        return webhookRepository.save(webhook.toBuilder()
                .updatedAt(LocalDateTime.now())
                .count(attempt)
                .build());
    }
}
