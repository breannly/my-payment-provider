package com.example.myuserservice.config;

import com.example.myuserservice.utils.JacksonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WebFluxConfig implements WebFluxConfigurer {

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        ObjectMapper mapper = JacksonUtils.generateCustomObjectMapper();

        Jackson2JsonEncoder encoder = new Jackson2JsonEncoder(mapper);
        Jackson2JsonDecoder decoder = new Jackson2JsonDecoder(mapper);

        configurer.defaultCodecs().jackson2JsonEncoder(encoder);
        configurer.defaultCodecs().jackson2JsonDecoder(decoder);
    }
}
