package me.ryzeon.reservations.mapper;

import me.ryzeon.reservations.dto.ReservationDto;
import me.ryzeon.reservations.model.Reservation;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper (componentModel = "spring")
public interface ReservationMapper extends Converter<Reservation, ReservationDto> {

    @Override
    ReservationDto convert(Reservation reservation);
}
