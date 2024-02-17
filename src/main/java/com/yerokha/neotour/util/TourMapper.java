package com.yerokha.neotour.util;

import com.yerokha.neotour.dto.CreateTourDto;
import com.yerokha.neotour.dto.TourDto;
import com.yerokha.neotour.dto.TourDtoFromList;
import com.yerokha.neotour.entity.Image;
import com.yerokha.neotour.entity.Location;
import com.yerokha.neotour.entity.Tour;

public class TourMapper {

    public static TourDto toDto(Tour tour) {
        return new TourDto(
                tour.getId(),
                tour.getTourName(),
                tour.getLocation().getLocality(),
                tour.getLocation().getCountry(),
                tour.getDescription(),
                tour.getImages().stream().map(Image::getImageUrl).toList(),
                tour.getReviews().stream().map(ReviewMapper::toDto).toList()
        );
    }

    public static TourDtoFromList toTourDtoFromList(Tour tour) {
        return new TourDtoFromList(
                tour.getId(),
                tour.getImages().get(0).getImageUrl(),
                tour.getTourName()
        );
    }

    public static Tour fromDto(CreateTourDto dto) {
        Tour tour = new Tour();

        tour.setTourName(dto.name());
        tour.setLocation(new Location(dto.locality(), dto.country(), dto.continent()));
        tour.setDescription(dto.description());
        tour.setFeatured(dto.isFeatured());

        return tour;
    }
}