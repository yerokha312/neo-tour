package com.yerokha.neotour.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "booking")
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long id;

    @ManyToOne
    private Tour tour;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "number_of_participants")
    private int numberOfParticipants;

    @Column(name = "comment")
    private String comment;
}

