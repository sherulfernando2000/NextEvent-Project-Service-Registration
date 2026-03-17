package lk.ijse.eca.registrationservice.exception;

public class RegistrationNotFoundException extends RuntimeException {

    public RegistrationNotFoundException(Long id) {
        super("Registration with ID '" + id + "' not found");
    }
}
