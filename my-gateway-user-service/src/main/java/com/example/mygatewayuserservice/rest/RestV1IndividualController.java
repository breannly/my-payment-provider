package com.example.mygatewayuserservice.rest;

import com.example.mygatewayuserservice.dto.UserGetMeResponse;
import com.example.mygatewayuserservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/individuals")
public class RestV1IndividualController {

    private final UserService userService;

    @GetMapping("/me")
    public Mono<ResponseEntity<UserGetMeResponse>> findById(@AuthenticationPrincipal Jwt jwt) {
        UUID individualId = getIdFromToken(jwt);
        log.info("Find individual by id: {}", individualId);
        return userService.findById(individualId)
                .map(response -> {
                    log.info("Response: {}", response);
                    return ResponseEntity.ok(response);
                });
    }

    private UUID getIdFromToken(Jwt jwt) {
        try {
            return UUID.fromString(jwt.getClaimAsString("user_id"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("");
        }
    }
}
