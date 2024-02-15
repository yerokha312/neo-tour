package com.yerokha.neotour.controller;

import com.yerokha.neotour.dto.ReviewDto;
import com.yerokha.neotour.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addReview(@RequestPart String dto, @RequestPart("image")MultipartFile image) {
        reviewService.addReview(dto, image);
    }

    @GetMapping("/{tourId}")
    public ResponseEntity<Page<ReviewDto>> getReviewsByTourId(@PathVariable Long tourId) {
        return ResponseEntity.ok(reviewService.getReviewsByTourId(tourId));
    }
}
