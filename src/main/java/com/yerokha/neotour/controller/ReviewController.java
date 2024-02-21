package com.yerokha.neotour.controller;

import com.yerokha.neotour.dto.ReviewDto;
import com.yerokha.neotour.service.ReviewService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/reviews")
@Tag(name = "Review", description = "Controller for retrieving reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(value = "hasAuthority('USER')")
    @Hidden
    public void addReview(@RequestPart String dto, @RequestPart("image") MultipartFile image) {
        reviewService.addReview(dto, image);
    }

    @GetMapping("/{tourId}")
    @Operation(
            summary = "Get all reviews by tour id",
            description = "Get all reviews by tour id",
            tags = {"review", "get"},
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "Tour not found", content = @Content)
            }
    )
    public ResponseEntity<Page<ReviewDto>> getReviewsByTourId(
            @PathVariable Long tourId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(reviewService.getReviewsByTourId(tourId, page, size));
    }
}
