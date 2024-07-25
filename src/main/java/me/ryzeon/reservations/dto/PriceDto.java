package me.ryzeon.reservations.dto;

import java.math.BigDecimal;

public record PriceDto(
        BigDecimal totalPrice,
        BigDecimal totalTaxes,
        BigDecimal basePrice
) {
}
