package me.ryzeon.reservations.services;

import me.ryzeon.reservations.dto.ReservationDto;

import java.util.List;

public interface ReservationService {

    List<ReservationDto> getAllReservations();

    ReservationDto getReservationById(Long id);

    ReservationDto createReservation(ReservationDto reservationDto);

    ReservationDto updateReservation(Long id, ReservationDto reservationDto);

    void deleteReservation(Long id);
}
