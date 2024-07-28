package me.ryzeon.reservations.dto;

import java.util.List;

public record ErrorDto(String description, List<String> reasons) {
}
