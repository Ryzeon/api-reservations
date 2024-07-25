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
    private List<Segment> segments;

    @ManyToOne(cascade = CascadeType.ALL)
    private Price price;
}
