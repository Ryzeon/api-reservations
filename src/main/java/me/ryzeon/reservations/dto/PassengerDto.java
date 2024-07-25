package me.ryzeon.reservations.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record PassengerDto(
        @NotBlank(message = "First name is required")
        String firstName,
        @NotBlank(message = "Last name is required")
        String lastName,
        String documentNumber,
        String documentType,

        @Past(message = "Birth date must be in the past")
        LocalDate birthDate) {

}
