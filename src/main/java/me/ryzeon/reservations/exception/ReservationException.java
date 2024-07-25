package me.ryzeon.reservations.exception;

import lombok.Getter;
import me.ryzeon.reservations.enums.ApiException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class ReservationException extends RuntimeException {

    private final HttpStatus status;

    private final String description;

    private final List<String> reasons;

    public ReservationException(ApiException error) {
        this.status = error.getHttpStatus();
        this.description = error.getMessage();
        this.reasons = List.of();
    }

    public ReservationException(HttpStatus status, String description, List<String> reasons) {
        this.status = status;
        this.description = description;
        this.reasons = reasons;
    }

}
