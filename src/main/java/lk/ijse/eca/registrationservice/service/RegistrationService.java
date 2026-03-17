package lk.ijse.eca.registrationservice.service;

import lk.ijse.eca.registrationservice.dto.RegistrationDto;

import java.util.List;

public interface RegistrationService {

    RegistrationDto createRegistration(RegistrationDto dto);

    RegistrationDto updateRegistration(Long id, RegistrationDto dto);

    void deleteRegistration(Long id);

    RegistrationDto getRegistration(Long id);

    List<RegistrationDto> getAllRegistrations();

    List<RegistrationDto> getRegistrationsByEventId(String eventId);
}
