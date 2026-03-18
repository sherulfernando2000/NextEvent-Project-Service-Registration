package lk.ijse.eca.registrationservice.client;

import lk.ijse.eca.registrationservice.exception.EventServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
@Slf4j
public class EventServiceClient {

    private final RestClient restClient;

    public EventServiceClient(@LoadBalanced RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("http://EVENT-SERVICE")
                .build();
    }

    public void validateEvent(String eventId) {
        log.debug("Validating event in Event-Service for ID: {}", eventId);
        try {
            restClient.get()
                    .uri("/api/v1/events/{id}", eventId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientException e) {
            log.error("Failed to validate event for ID: {}", eventId, e);
            throw new EventServiceException(
                    "Unable to validate event: " + eventId, e);
        }
    }
}
