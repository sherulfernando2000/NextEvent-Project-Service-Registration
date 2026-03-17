package lk.ijse.eca.registrationservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lk.ijse.eca.registrationservice.dto.RegistrationDto;
import lk.ijse.eca.registrationservice.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/registrations")
@RequiredArgsConstructor
@Slf4j
@Validated
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RegistrationDto> createRegistration(
            @Valid @RequestBody RegistrationDto dto) {
        log.info("POST /api/v1/registrations - participantId: {}, eventId: {}", dto.getParticipantId(), dto.getEventId());
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationService.createRegistration(dto));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrationDto> getRegistration(
            @PathVariable @Positive(message = "Registration ID must be a positive number") Long id) {
        log.info("GET /api/v1/registrations/{}", id);
        return ResponseEntity.ok(registrationService.getRegistration(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RegistrationDto>> getAllRegistrations() {
        log.info("GET /api/v1/registrations - retrieving all registrations");
        return ResponseEntity.ok(registrationService.getAllRegistrations());
    }

    @GetMapping(params = "eventId", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RegistrationDto>> getRegistrationsByEventId(
            @RequestParam @NotBlank(message = "Event ID must not be blank") String eventId) {
        log.info("GET /api/v1/registrations?eventId={} - retrieving registrations by event", eventId);
        return ResponseEntity.ok(registrationService.getRegistrationsByEventId(eventId));
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RegistrationDto> updateRegistration(
            @PathVariable @Positive(message = "Registration ID must be a positive number") Long id,
            @Valid @RequestBody RegistrationDto dto) {
        log.info("PUT /api/v1/registrations/{}", id);
        return ResponseEntity.ok(registrationService.updateRegistration(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistration(
            @PathVariable @Positive(message = "Registration ID must be a positive number") Long id) {
        log.info("DELETE /api/v1/registrations/{}", id);
        registrationService.deleteRegistration(id);
        return ResponseEntity.noContent().build();
    }
}
