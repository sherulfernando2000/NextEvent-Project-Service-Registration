package lk.ijse.eca.registrationservice.mapper;

import lk.ijse.eca.registrationservice.dto.RegistrationDto;
import lk.ijse.eca.registrationservice.dto.ParticipantDto;
import lk.ijse.eca.registrationservice.entity.Registration;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RegistrationMapper {

    @Mapping(target = "participant", source = "participant")
    RegistrationDto toDto(Registration registration, ParticipantDto participant);

    @Mapping(target = "id", ignore = true)
    Registration toEntity(RegistrationDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntity(RegistrationDto dto, @MappingTarget Registration registration);
}
