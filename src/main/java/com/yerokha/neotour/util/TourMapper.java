package com.yerokha.neotour.util;

import com.yerokha.neotour.dto.CreateTourDto;
import com.yerokha.neotour.dto.TourDto;
import com.yerokha.neotour.dto.TourDtoFromList;
import com.yerokha.neotour.entity.Tour;

public class TourMapper {

    public static TourDto toDto(Tour tour) {
        return new TourDto(
                tour.getId(),
                tour.getName(),
                tour.getLocation(),
                tour.getDescription(),
                tour.getImages(),
                tour.getReviews().stream().map(ReviewMapper::toDto).toList()
        );
    }

    public static TourDtoFromList tourDtoFromList(Tour tour) {
        return new TourDtoFromList(
                tour.getId(),
                tour.getImages().get(0),
                tour.getName()
        );
    }

    public static Tour fromDto(CreateTourDto dto) {
        Tour tour = new Tour();

        tour.setName(dto.name());
        tour.setLocation(dto.location());
        tour.setDescription(dto.description());
        tour.setImages(dto.images());

        return tour;
    }
}