package me.ryzeon.reservations.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ReservationDto(
        Long id,

        @Valid
        @NotEmpty(message = "At least one passenger is required")
        List<PassengerDto> passengers,

        @Valid
        ItineraryDto itinerary
) {
}
