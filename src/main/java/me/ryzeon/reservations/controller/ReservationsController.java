package me.ryzeon.reservations.controller;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import me.ryzeon.reservations.controller.resource.ReservationResource;
import me.ryzeon.reservations.dto.ReservationDto;
import me.ryzeon.reservations.enums.ApiException;
import me.ryzeon.reservations.exception.ReservationException;
import me.ryzeon.reservations.services.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@AllArgsConstructor
public class ReservationsController implements ReservationResource {

    private final ReservationService reservationService;

    private final Logger LOGGER = LoggerFactory.getLogger(ReservationsController.class);

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getReservations() {
        List<ReservationDto> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> getReservation(@Valid @PathVariable Long id) {
        ReservationDto reservation = reservationService.getReservationById(id);
        return ResponseEntity.ok(reservation);
    }

    @PostMapping
    @RateLimiter(name = "post-reservation", fallbackMethod = "fallbackPost")
    public ResponseEntity<ReservationDto> createReservation(@Valid @RequestBody ReservationDto reservationDto) {
        ReservationDto reservation = reservationService.createReservation(reservationDto);
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDto> updateReservation(@Min(1) @PathVariable Long id,
            @Valid @RequestBody ReservationDto reservationDto) {
        ReservationDto reservation = reservationService.updateReservation(id, reservationDto);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@Min(1) @PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<ReservationDto> fallbackPost(ReservationDto reservationDto, RequestNotPermitted ex) {
        LOGGER.error("Rate limit reached for post-reservation");
        throw new ReservationException(ApiException.EXCEED_NUMBER_OPERATIONS);
    }
}
