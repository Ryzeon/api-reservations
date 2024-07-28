package me.ryzeon.reservations.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String documentNumber;
    private String documentType;
    private LocalDate birthDate;

    public Passenger() {
    }

    public Passenger(String firstName, String lastName, String documentNumber, String documentType,
            LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentNumber = documentNumber;
        this.documentType = documentType;
        this.birthDate = birthDate;
    }
}
