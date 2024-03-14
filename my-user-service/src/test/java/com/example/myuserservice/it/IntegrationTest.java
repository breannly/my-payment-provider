package com.example.myuserservice.it;

import com.example.myuserservice.dto.IndividualNewDto;
import com.example.myuserservice.service.KeycloakService;
import com.example.myuserservice.utils.TestDataUtils;
import org.junit.jupiter.api.Test;
import org.keycloak.representations.AccessTokenResponse;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

public class IntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private KeycloakService keycloakService;

    @Test
    void test_save_individual() {
        IndividualNewDto individualNewDto = TestDataUtils.createIndividualNewDto();
        Mockito.when(keycloakService.save(Mockito.any())).thenReturn(Mono.just(new AccessTokenResponse()));
        webTestClient.post().uri("/api/v1/individuals")
                .bodyValue(individualNewDto)
                .exchange()
                .expectStatus().isCreated();
    }
}
