package me.ryzeon.reservations.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Passenger> passengers;

    @ManyToOne(cascade = CascadeType.ALL)
    private Itinerary itinerary;
}
