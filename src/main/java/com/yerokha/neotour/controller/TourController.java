package com.yerokha.neotour.controller;

import com.yerokha.neotour.dto.TourDto;
import com.yerokha.neotour.dto.TourDtoFromList;
import com.yerokha.neotour.entity.Tour;
import com.yerokha.neotour.service.TourService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.Map;

@RestController
@RequestMapping("/v1/tours")
@Tag(name = "Tour", description = "Controller for retrieving tours")
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
    @Operation(
            summary = "Get tour by id",
            description = "Get tour by id",
            tags = {"tour", "get"},
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "Tour not found", content = @Content)
            }
    )
    public ResponseEntity<TourDto> getTourById(@PathVariable Long id) {
        tourService.incrementViewCount(id);
        return ResponseEntity.ok(tourService.getTourById(id));
    }

    @GetMapping
    @Operation(
            summary = "Get tours",
            description = "Get tours based on parameter.",
            tags = {"tour", "get"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Paged list of tours"),
                    @ApiResponse(responseCode = "400", description = "Invalid parameter", content = @Content)
            },
            parameters = {
                    @Parameter(name = "param", description = "Parameter to search by",
                            examples = {
                                    @ExampleObject(name = "Popular tours", value = "popular"),
                                    @ExampleObject(name = "Featured tours", value = "featured"),
                                    @ExampleObject(name = "Most visited tours", value = "visited"),
                                    @ExampleObject(name = "Tours by continent", value = "asia"),
                                    @ExampleObject(name = "Recommended tours", value = "recommended")
                            }, required = true),
                    @Parameter(name = "page", description = "Page number", example = "0"),
                    @Parameter(name = "size", description = "Page size", example = "3"),
                    @Parameter(name = "month", description = "Recommended in month...", example = "3")
            }
    )
    public ResponseEntity<Page<TourDtoFromList>> getTours(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(tourService.getTours(params));
    }

    @PutMapping
    @Hidden
    public void updateTour(@RequestBody Tour tour) {
        tourService.updateTour(tour);
    }

    @DeleteMapping("/{id}")
    @Hidden
    public void deleteTour(@PathVariable Long id) {
        tourService.deleteTourById(id);
    }

}
