package me.ryzeon.reservations.dto;

import jakarta.validation.Valid;

import java.util.List;

public record ItineraryDto(@Valid List<SegmentDto> segments, PriceDto price) {
}
