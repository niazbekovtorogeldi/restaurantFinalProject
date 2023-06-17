package peaksoft.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Pattern;
import peaksoft.enums.Role;
import peaksoft.validation.PhoneNumberValid;

public record SignUpRequest(
        String firstName,
        String lastName,
        int age,

        String email,
        String password,
        @PhoneNumberValid
        String phoneNumber,
        @Enumerated(EnumType.STRING)
        Role role,
        int experience
) {
}
