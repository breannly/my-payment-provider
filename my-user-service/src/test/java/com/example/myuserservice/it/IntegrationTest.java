package com.example.myuserservice.it;

import com.example.myuserservice.dto.IndividualNewDto;
import com.example.myuserservice.entity.Individual;
import com.example.myuserservice.entity.ProfileHistory;
import com.example.myuserservice.entity.profile.Profile;
import com.example.myuserservice.entity.user.User;
import com.example.myuserservice.repository.IndividualRepository;
import com.example.myuserservice.repository.ProfileHistoryRepository;
import com.example.myuserservice.repository.ProfileRepository;
import com.example.myuserservice.repository.UserRepository;
import com.example.myuserservice.utils.TestDataUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

public class IntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IndividualRepository individualRepository;
    @Autowired
    private ProfileHistoryRepository profileHistoryRepository;

    @Test
    void test_save_individual() {
        IndividualNewDto individualNewDto = TestDataUtils.createIndividualNewDto();

        webTestClient.post().uri("/api/v1/individuals")
                .bodyValue(individualNewDto)
                .exchange()
                .expectStatus().isOk();

        Profile profile = profileRepository.findAll().blockFirst();
        assertsProfile(profile, individualNewDto);
        User user = userRepository.findAll().blockFirst();
        assert user != null;
        user.setProfile(profile);
        assertsUser(user, individualNewDto);
        Individual individual = individualRepository.findAll().blockFirst();
        assert individual != null;
        individual.setUser(user);
        assertsIndividual(individual, individualNewDto);
        ProfileHistory profileHistory = profileHistoryRepository.findAll().blockFirst();
        assert profileHistory != null;
        profileHistory.setProfile(profile);
        profileHistory.setUser(user);
        assertsProfileHistory(profileHistory, individualNewDto);
    }

    private void assertsProfile(Profile profile, IndividualNewDto individualNewDto) {
        Assertions.assertNotNull(profile);
        Assertions.assertNotNull(profile.getId());
        Assertions.assertEquals(individualNewDto.getCreatedAt(), profile.getUpdatedAt());
        Assertions.assertEquals(individualNewDto.getCreatedAt(), profile.getCreatedAt());
        Assertions.assertEquals(individualNewDto.getUsername(), profile.getUsername());
        Assertions.assertEquals(individualNewDto.getPassword(), profile.getPassword());
        Assertions.assertEquals(individualNewDto.getSecretKey(), profile.getSecretKey());
        Assertions.assertEquals(individualNewDto.getPassword() != null, profile.getIsPasswordSet());
    }

    private void assertsUser(User user, IndividualNewDto individualNewDto) {
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals(user.getProfileId(), user.getProfile().getId());
        Assertions.assertEquals(individualNewDto.getCreatedAt(), user.getUpdatedAt());
        Assertions.assertEquals(individualNewDto.getCreatedAt(), user.getCreatedAt());
        Assertions.assertEquals(individualNewDto.getFirstName(), user.getFirstName());
        Assertions.assertEquals(individualNewDto.getLastName(), user.getLastName());
        Assertions.assertEquals(individualNewDto.getEmail(), user.getEmail());
        Assertions.assertEquals(individualNewDto.getPhoneNumber(), user.getPhoneNumber());
        Assertions.assertEquals(individualNewDto.getEmail() != null || individualNewDto.getPhoneNumber() != null, user.getFilled());
    }

    private void assertsIndividual(Individual individual, IndividualNewDto individualNewDto) {
        Assertions.assertNotNull(individual);
        Assertions.assertNotNull(individual.getId());
        Assertions.assertEquals(individual.getUserId(), individual.getUser().getId());
        Assertions.assertEquals(individualNewDto.getCreatedAt(), individual.getUpdatedAt());
        Assertions.assertEquals(individualNewDto.getCreatedAt(), individual.getCreatedAt());
        Assertions.assertEquals(individualNewDto.getDateOfBirth(), individual.getDateOfBirth());
    }

    private void assertsProfileHistory(ProfileHistory profileHistory, IndividualNewDto individualNewDto) {
        Assertions.assertNotNull(profileHistory);
        Assertions.assertNotNull(profileHistory.getId());
        Assertions.assertEquals(profileHistory.getProfileId(), profileHistory.getProfile().getId());
        Assertions.assertEquals(profileHistory.getUserId(), profileHistory.getUser().getId());
        Assertions.assertEquals(individualNewDto.getCreatedAt(), profileHistory.getCreatedAt());
        Assertions.assertNotNull(profileHistory.getChangedValues());
        Assertions.assertEquals(profileHistory.getChangedValues().getUsername(), individualNewDto.getUsername());
        Assertions.assertEquals(profileHistory.getChangedValues().getPassword(), individualNewDto.getPassword());
        Assertions.assertEquals(profileHistory.getChangedValues().getSecretKey(), individualNewDto.getSecretKey());
        Assertions.assertEquals(profileHistory.getChangedValues().getFirstName(), individualNewDto.getFirstName());
        Assertions.assertEquals(profileHistory.getChangedValues().getLastName(), individualNewDto.getLastName());
        Assertions.assertEquals(profileHistory.getChangedValues().getEmail(), individualNewDto.getEmail());
        Assertions.assertEquals(profileHistory.getChangedValues().getPhoneNumber(), individualNewDto.getPhoneNumber());
        Assertions.assertEquals(profileHistory.getChangedValues().getDateOfBirth(), individualNewDto.getDateOfBirth());
    }
}
