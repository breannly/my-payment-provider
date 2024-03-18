package com.example.mygatewayuserservice.rest;

import com.example.mygatewayuserservice.dto.UserLoginRequest;
import com.example.mygatewayuserservice.dto.UserLoginResponse;
import com.example.mygatewayuserservice.dto.UserRegistrationRequest;
import com.example.mygatewayuserservice.dto.UserRegistrationResponse;
import com.example.mygatewayuserservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class RestV1AuthController {

    private final AuthService authService;

    @PostMapping("/registration")
    public Mono<ResponseEntity<UserRegistrationResponse>> register(@RequestBody @Validated UserRegistrationRequest request) {
        log.info("Request: {}", request);
        return authService.register(request)
                .map(response -> {
                    log.info("Response: {}", response);
                    return ResponseEntity.ok(response);
                });
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<UserLoginResponse>> login(@RequestBody @Validated UserLoginRequest request) {
        log.info("Request: {}", request);
        return authService.login(request)
                .map(response -> {
                    log.info("Response: {}", response);
                    return ResponseEntity.ok(response);
                });
    }
}
