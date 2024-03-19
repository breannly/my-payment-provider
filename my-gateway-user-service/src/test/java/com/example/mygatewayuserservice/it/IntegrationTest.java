package com.example.mygatewayuserservice.it;

import com.example.mygatewayuserservice.dto.*;
import com.example.mygatewayuserservice.service.IndividualService;
import com.example.mygatewayuserservice.utils.TestDataUtils;
import com.example.mypaymentprovider.api.individual.model.Individual;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

public class IntegrationTest extends AbstractIntegrationTest {

    @MockBean
    private IndividualService individualService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void test_registration() {
        UserRegistrationRequest request = TestDataUtils.createUserRegistrationRequest();
        Individual individual = TestDataUtils.createIndividual();

        Mockito.when(individualService.save(Mockito.any())).thenReturn(Mono.just(individual));

        UserRegistrationResponse response = webTestClient.post().uri("api/v1/auth/registration")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserRegistrationResponse.class).returnResult()
                .getResponseBody();

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getAccessToken());
        Assertions.assertNotNull(response.getExpiresIn());
        Assertions.assertNotNull(response.getRefreshToken());
        Assertions.assertNotNull(response.getTokenType());
    }

    @Test
    void test_login() {
        UserRegistrationRequest requestRegistration = TestDataUtils.createUserRegistrationRequest();
        Individual individual = TestDataUtils.createIndividual();

        Mockito.when(individualService.save(Mockito.any())).thenReturn(Mono.just(individual));

        webTestClient.post().uri("api/v1/auth/registration")
                .bodyValue(requestRegistration)
                .exchange()
                .expectStatus().isOk();

        UserLoginRequest requestLogin = new UserLoginRequest();
        requestLogin.setEmail(requestRegistration.getEmail());
        requestLogin.setUsername(requestRegistration.getUsername());
        requestLogin.setPassword(requestRegistration.getPassword());

        UserLoginResponse responseLogin = webTestClient.post().uri("api/v1/auth/login")
                .bodyValue(requestLogin)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserLoginResponse.class).returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseLogin);
        Assertions.assertNotNull(responseLogin.getAccessToken());
        Assertions.assertNotNull(responseLogin.getExpiresIn());
        Assertions.assertNotNull(responseLogin.getRefreshToken());
        Assertions.assertNotNull(responseLogin.getTokenType());
    }

    @Test
    void test_find_individual() {
        UserRegistrationRequest requestRegistration = TestDataUtils.createUserRegistrationRequest();
        Individual individual = TestDataUtils.createIndividual();
        individual.setUsername(requestRegistration.getUsername());

        Mockito.when(individualService.save(Mockito.any())).thenReturn(Mono.just(individual));
        Mockito.when(individualService.findById(Mockito.any())).thenReturn(Mono.just(individual));

        UserRegistrationResponse responseRegistration = webTestClient.post().uri("api/v1/auth/registration")
                .bodyValue(requestRegistration)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserRegistrationResponse.class).returnResult()
                .getResponseBody();

        assert responseRegistration != null;
        String accessToken = "Bearer " + responseRegistration.getAccessToken();

        UserGetMeResponse responseGetMe = webTestClient.get().uri("api/v1/users/me")
                .header("Authorization", accessToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserGetMeResponse.class).returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseGetMe);
        Assertions.assertEquals(requestRegistration.getUsername(), responseGetMe.getUsername());
    }
}
