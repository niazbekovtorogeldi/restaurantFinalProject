package peaksoft.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import peaksoft.enums.Role;
import peaksoft.validation.EmailValid;
import peaksoft.validation.PhoneNumberValid;

import java.time.ZonedDateTime;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        int age,
        ZonedDateTime createdDate,
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
    public UserResponse {
    }
}
