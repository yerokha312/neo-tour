package com.yerokha.neotour.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yerokha.neotour.dto.CreateReviewDto;
import com.yerokha.neotour.dto.ReviewDto;
import com.yerokha.neotour.entity.Review;
import com.yerokha.neotour.exception.NotFoundException;
import com.yerokha.neotour.repository.ReviewRepository;
import com.yerokha.neotour.repository.TourRepository;
import com.yerokha.neotour.util.ReviewMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final TourRepository tourRepository;
    private final ImageService imageService;

    private final ObjectMapper mapper = new ObjectMapper();


    public ReviewService(ReviewRepository reviewRepository, TourRepository tourRepository, ImageService imageService) {
        this.reviewRepository = reviewRepository;
        this.tourRepository = tourRepository;
        this.imageService = imageService;
    }

    public void addReview(String json, MultipartFile image) {
        CreateReviewDto dto;
        try {
            dto = mapper.readValue(json, CreateReviewDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Review review = ReviewMapper.fromDto(dto);
        review.setTour(tourRepository.findById(dto.tourId()).orElseThrow(NotFoundException::new));
        review.setProfilePicture(imageService.processImage(image));

        reviewRepository.save(review);
    }

    public Page<ReviewDto> getReviewsByTourId(Long tourId, int page, int size) {
        if (!tourRepository.existsById(tourId)) {
            throw new NotFoundException();
        }
        return reviewRepository.findAllByTour_Id(tourId, PageRequest.of(page, size)).map(ReviewMapper::toDto);
    }
}
