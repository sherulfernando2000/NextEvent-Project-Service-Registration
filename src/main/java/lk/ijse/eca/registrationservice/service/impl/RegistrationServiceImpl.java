package lk.ijse.eca.registrationservice.service.impl;

import lk.ijse.eca.registrationservice.client.EventServiceClient;
import lk.ijse.eca.registrationservice.client.ParticipantServiceClient;
import lk.ijse.eca.registrationservice.dto.RegistrationDto;
import lk.ijse.eca.registrationservice.dto.ParticipantDto;
import lk.ijse.eca.registrationservice.entity.Registration;
import lk.ijse.eca.registrationservice.exception.RegistrationNotFoundException;
import lk.ijse.eca.registrationservice.mapper.RegistrationMapper;
import lk.ijse.eca.registrationservice.repository.RegistrationRepository;
import lk.ijse.eca.registrationservice.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final RegistrationMapper registrationMapper;
    private final ParticipantServiceClient participantServiceClient;
    private final EventServiceClient eventServiceClient;

    @Override
    @Transactional
    public RegistrationDto createRegistration(RegistrationDto dto) {
        log.debug("Creating registration for participant: {}, event: {}", dto.getParticipantId(), dto.getEventId());

        // Validate both references before any DB write
        ParticipantDto participant = participantServiceClient.getParticipant(dto.getParticipantId());
        eventServiceClient.validateEvent(dto.getEventId());

        Registration registration = registrationMapper.toEntity(dto);
        Registration saved = registrationRepository.save(registration);
        log.info("Registration created successfully with ID: {}", saved.getId());
        return registrationMapper.toDto(saved, participant);
    }

    @Override
    @Transactional
    public RegistrationDto updateRegistration(Long id, RegistrationDto dto) {
        log.debug("Updating registration with ID: {}", id);

        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Registration not found for update: {}", id);
                    return new RegistrationNotFoundException(id);
                });

        // Validate both references before updating
        ParticipantDto participant = participantServiceClient.getParticipant(dto.getParticipantId());
        eventServiceClient.validateEvent(dto.getEventId());

        registrationMapper.updateEntity(dto, registration);
        Registration updated = registrationRepository.save(registration);
        log.info("Registration updated successfully: {}", id);
        return registrationMapper.toDto(updated, participant);
    }

    @Override
    @Transactional
    public void deleteRegistration(Long id) {
        log.debug("Deleting registration with ID: {}", id);

        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Registration not found for deletion: {}", id);
                    return new RegistrationNotFoundException(id);
                });

        registrationRepository.delete(registration);
        log.info("Registration deleted successfully: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public RegistrationDto getRegistration(Long id) {
        log.debug("Fetching registration with ID: {}", id);

        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Registration not found: {}", id);
                    return new RegistrationNotFoundException(id);
                });

        ParticipantDto participant = participantServiceClient.getParticipant(registration.getParticipantId());
        return registrationMapper.toDto(registration, participant);
    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<RegistrationDto> getAllRegistrations() {
//        log.debug("Fetching all registrations");
//
//        List<RegistrationDto> registrations = registrationRepository.findAll()
//                .stream()
//                .map(registration -> {
//                    ParticipantDto participant = participantServiceClient.getParticipant(registration.getParticipantId());
//                    return registrationMapper.toDto(registration, participant);
//                })
//                .toList();
//
//        log.debug("Fetched {} registration(s)", registrations.size());
//        return registrations;
//    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistrationDto> getAllRegistrations() {
        log.debug("Fetching all registrations and filtering out missing participants");

        return registrationRepository.findAll()
                .stream()
                .map(registration -> {
                    try {
                        // 1. Try to get the participant
                        ParticipantDto participant = participantServiceClient.getParticipant(registration.getParticipantId());
                        // 2. If successful, return the mapped DTO
                        return registrationMapper.toDto(registration, participant);
                    } catch (Exception e) {
                        // 3. If Participant is not found (404) or Service is down, log and return null
                        log.warn("Skipping registration {} because participant {} could not be retrieved",
                                registration.getId(), registration.getParticipantId());
                        return null;
                    }
                })
                // 4. Filter out the nulls (the ones that failed)
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistrationDto> getRegistrationsByEventId(String eventId) {
        log.debug("Fetching registrations for eventId: {}", eventId);

        List<RegistrationDto> registrations = registrationRepository.findByEventIdOrderByDateDescIdDesc(eventId)
                .stream()
                .map(registration -> {
                    ParticipantDto participant = participantServiceClient.getParticipant(registration.getParticipantId());
                    return registrationMapper.toDto(registration, participant);
                })
                .toList();

        log.debug("Fetched {} registration(s) for eventId: {}", registrations.size(), eventId);
        return registrations;
    }
}
