package com.example.mygatewayuserservice.config;

import com.example.mygatewayuserservice.utils.JacksonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return JacksonUtils.generateCustomObjectMapper();
    }
}
