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
@Table(name = "location")
@Data
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;

    @Column(name = "locality")
    private String locality;

    @Column(name = "country")
    private String country;

    @Column(name = "continent")
    private String continent;

    @OneToMany(mappedBy = "location")
    private List<Tour> tours;

    public Location() {
    }

    public Location(String locality, String country, String continent) {
        this.locality = locality;
        this.country = country;
        this.continent = continent;
    }
}
