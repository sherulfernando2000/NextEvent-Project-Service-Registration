package lk.ijse.eca.registrationservice.client;

import lk.ijse.eca.registrationservice.dto.ParticipantDto;
import lk.ijse.eca.registrationservice.exception.ParticipantServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
@Slf4j
public class ParticipantServiceClient {

    private final RestClient restClient;

    public ParticipantServiceClient(@LoadBalanced  RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("http://PARTICIPANT-SERVICE")
                .build();
    }

    public ParticipantDto getParticipant(String participantId) {
        log.debug("Fetching participant from Participant-Service for ID: {}", participantId);
        try {
            return restClient.get()
                    .uri("/api/v1/participants/{id}", participantId)
                    .retrieve()
                    .body(ParticipantDto.class);
        } catch (RestClientException e) {
            log.error("Failed to fetch participant details for ID: {}", participantId, e);
            throw new ParticipantServiceException(
                    "Unable to retrieve participant details for: " + participantId, e);
        }
    }
}
