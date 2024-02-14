package com.yerokha.neotour.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "tour")
@Data
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "recommended_months")
    private int recommendedMonths;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "tour")
    private List<Image> images;

    @OneToMany(mappedBy = "tour")
    private List<Review> reviews;

    @Column(name = "booking_count")
    private Long bookingCount;

    @Column(name = "view_count")
    private Long viewCount;

}
