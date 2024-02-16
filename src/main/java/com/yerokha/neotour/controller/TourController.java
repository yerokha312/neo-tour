package com.yerokha.neotour.controller;

import com.yerokha.neotour.dto.TourDto;
import com.yerokha.neotour.dto.TourDtoFromList;
import com.yerokha.neotour.entity.Tour;
import com.yerokha.neotour.service.TourService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/v1/tours")
public class TourController {

    private final TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Hidden
    public void addTour(@RequestPart String dto, @RequestPart("images") List<MultipartFile> images) {
        tourService.addTour(dto, images);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourDto> getTourById(@PathVariable Long id) {
        return ResponseEntity.ok(tourService.getTourById(id));
    }

    @GetMapping
    public ResponseEntity<List<TourDtoFromList>> getTours(@RequestParam("param") String param) {
        return ResponseEntity.ok(tourService.getTours(param));
    }

    @GetMapping("/recommended/{month}")
    public ResponseEntity<Page<TourDtoFromList>> getRecommendedTours(@PathVariable int month) {
        return ResponseEntity.ok(tourService.getRecommendedTours(month));
    }

    @PutMapping
    @Hidden
    public void updateTour(@RequestBody Tour tour) {
        tourService.updateTour(tour);
    }

    @DeleteMapping("/{id}")
    public void deleteTour(@PathVariable Long id) {
        tourService.deleteTourById(id);
    }
}
