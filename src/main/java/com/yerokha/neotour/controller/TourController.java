package com.yerokha.neotour.controller;

import com.yerokha.neotour.dto.TourDto;
import com.yerokha.neotour.dto.TourDtoFromList;
import com.yerokha.neotour.entity.Tour;
import com.yerokha.neotour.service.TourService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/tours")
public class TourController {

    private final TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTour(@RequestBody Tour tour) {
        tourService.addTour(tour);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourDto> getTourById(@PathVariable Long id) {
        return ResponseEntity.ok(tourService.getTourById(id));
    }

    @GetMapping("/popular")
    public ResponseEntity<List<TourDtoFromList>> get9PopularTours() {
        return ResponseEntity.ok(tourService.getPopularTours());
    }

    @GetMapping("/most-visited")
    public ResponseEntity<List<TourDtoFromList>> getMostVisitedTours() {
        return ResponseEntity.ok(tourService.getMostVisitedTours());
    }

    @GetMapping("/recommended/{month}")
    public ResponseEntity<Page<TourDtoFromList>> getRecommendedTours(@PathVariable String month) {
        return ResponseEntity.ok(tourService.getRecommendedTours(month));
    }

    @PutMapping("/")
    public void updateTour(@RequestBody Tour tour) {
        tourService.updateTour(tour);
    }
}
