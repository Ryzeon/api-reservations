package me.ryzeon.reservations.services.impl;

import lombok.AllArgsConstructor;
import me.ryzeon.reservations.conector.CatalogConnector;
import me.ryzeon.reservations.conector.response.CityDto;
import me.ryzeon.reservations.dto.ReservationDto;
import me.ryzeon.reservations.dto.SegmentDto;
import me.ryzeon.reservations.enums.ApiException;
import me.ryzeon.reservations.exception.ReservationException;
import me.ryzeon.reservations.model.Reservation;
import me.ryzeon.reservations.repository.ReservationRepository;
import me.ryzeon.reservations.services.ReservationService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repository;

    private final ConversionService conversionService;

    private final CatalogConnector catalogConnector;

    @Override
    public List<ReservationDto> getAllReservations() {
        return conversionService.convert(repository.findAll(), List.class);
    }

    @Override
    public ReservationDto getReservationById(Long id) {
        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new ReservationException(ApiException.RESERVATION_NOT_FOUND));
        return conversionService.convert(reservation, ReservationDto.class);
    }

    @Override
    public ReservationDto createReservation(ReservationDto reservationDto) {
        if (reservationDto.id() != null) {
            throw new ReservationException(ApiException.RESERVATION_WITH_SAME_ID);
        }
        checkCity(reservationDto);
        Reservation reservation = conversionService.convert(reservationDto, Reservation.class);

        System.out.println("Reservation: " + reservation.toString());
        Reservation savedReservation = repository.save(Objects.requireNonNull(reservation));
        return conversionService.convert(savedReservation, ReservationDto.class);
    }

    @Override
    public ReservationDto updateReservation(Long id, ReservationDto reservationDto) {
        if (!repository.existsById(id)) {
            throw new ReservationException(ApiException.RESERVATION_NOT_FOUND);
        }
        Reservation reservation = conversionService.convert(reservationDto, Reservation.class);
        assert reservation != null;
        reservation.setId(id);
        Reservation savedReservation = repository.save(Objects.requireNonNull(reservation));
        return conversionService.convert(savedReservation, ReservationDto.class);
    }

    @Override
    public void deleteReservation(Long id) {
        if (!repository.existsById(id)) {
            throw new ReservationException(ApiException.RESERVATION_NOT_FOUND);
        }
        repository.deleteById(id);
    }

    private void checkCity(ReservationDto reservationDto) {
        for (SegmentDto segment : reservationDto.itinerary().segments()) {
            CityDto origin = catalogConnector.getCity(segment.origin());
            CityDto destination = catalogConnector.getCity(segment.destination());

            if (origin == null || destination == null) {
                throw new ReservationException(ApiException.VALIDATION_ERROR);
            } else {
                System.out.println("Origin: " + origin);
                System.out.println("Destination: " + destination);
            }
        }
    }
}
