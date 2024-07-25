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

    @OneToOne(cascade = CascadeType.ALL)
    private Itinerary itinerary;

    public Reservation() {
        this.passengers = null;
        this.itinerary = null;
    }

    public Reservation(List<Passenger> passengers, Itinerary itinerary) {
        this.passengers = passengers;
        this.itinerary = itinerary;
    }
}
