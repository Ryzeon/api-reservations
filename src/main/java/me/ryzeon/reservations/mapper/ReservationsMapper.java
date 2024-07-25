package me.ryzeon.reservations.mapper;

import me.ryzeon.reservations.dto.ReservationDto;
import me.ryzeon.reservations.model.Reservation;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

@Mapper (componentModel = "spring")
public interface ReservationsMapper extends Converter<List<Reservation>, List<ReservationDto>> {

    @Override
    List<ReservationDto> convert(List<Reservation> reservation);
}
