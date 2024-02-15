package com.yerokha.neotour.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
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

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "recommended_months")
    private int recommendedMonths;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "tour")
    private List<Image> images;

    @OneToMany(mappedBy = "tour")
    private List<Review> reviews;

    @Column(name = "booking_count")
    private long bookingCount;

    @Column(name = "view_count")
    private long viewCount;

    public void addImage(Image image) {
        if (images == null) {
            images = new ArrayList<>();
        }
        image.setTour(this);
        images.add(image);
    }

}
