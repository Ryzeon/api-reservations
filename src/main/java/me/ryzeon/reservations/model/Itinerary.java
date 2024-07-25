package me.ryzeon.reservations.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Itinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    private final List<Segment> segments;

    @OneToOne(cascade = CascadeType.ALL)
    private final Price price;

    public Itinerary() {
        this.segments = null;
        this.price = null;
    }

    public Itinerary(List<Segment> segments, Price price) {
        this.segments = segments;
        this.price = price;
    }
}
