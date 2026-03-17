package lk.ijse.eca.registrationservice.repository;

import lk.ijse.eca.registrationservice.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    List<Registration> findByEventIdOrderByDateDescIdDesc(String eventId);
}
