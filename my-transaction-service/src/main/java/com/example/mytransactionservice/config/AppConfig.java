package com.example.mytransactionservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableScheduling
public class AppConfig {

    @Bean
    public WebClient webClient(ObjectMapper objectMapper) {
        ExchangeStrategies strategies = ExchangeStrategies
                .builder()
                .codecs(clientDefaultCodecsConfigurer -> clientDefaultCodecsConfigurer.defaultCodecs()
                        .jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON)))
                .build();

        return WebClient.builder()
                .exchangeStrategies(strategies)
                .build();
    }
}
