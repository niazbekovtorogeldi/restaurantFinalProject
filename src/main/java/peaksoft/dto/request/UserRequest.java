package peaksoft.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import peaksoft.enums.Role;
import peaksoft.validation.EmailValid;
import peaksoft.validation.PhoneNumberValid;

import java.time.ZonedDateTime;

public record UserRequest(
        String firstName,
        String lastName,
        int age,
        @EmailValid
        String email,
        @Size(min = 7, max = 15, message = "Password must be exactly 7 characters long")
        String password,
        @PhoneNumberValid
        String phoneNumber,
        @Enumerated(EnumType.STRING)
        Role role,
        int experience
) {
}
