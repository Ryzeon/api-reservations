package me.ryzeon.reservations.dto;

import jakarta.validation.constraints.NotBlank;
import me.ryzeon.reservations.validator.CityFormatConstraint;

public record SegmentDto(
        @CityFormatConstraint
        String origin,
        @CityFormatConstraint
        String destination,
        @NotBlank(message = "Departure is required")
        String departure,
        @NotBlank(message = "Arrival is required")
        String arrival,
        @NotBlank(message = "Carrier is required")
        String carrier
) {
}
