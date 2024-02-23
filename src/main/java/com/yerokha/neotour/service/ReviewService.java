package com.yerokha.neotour.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yerokha.neotour.dto.CreateReviewDto;
import com.yerokha.neotour.dto.ReviewDto;
import com.yerokha.neotour.entity.AppUser;
import com.yerokha.neotour.entity.Image;
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
    private final UserService userService;

    private final ObjectMapper mapper = new ObjectMapper();


    public ReviewService(ReviewRepository reviewRepository, TourRepository tourRepository, ImageService imageService, UserService userService) {
        this.reviewRepository = reviewRepository;
        this.tourRepository = tourRepository;
        this.imageService = imageService;
        this.userService = userService;
    }

    public void addReview(String json, MultipartFile image, String username) {
        CreateReviewDto dto;
        try {
            dto = mapper.readValue(json, CreateReviewDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (!tourRepository.existsById(dto.tourId())) {
            throw new NotFoundException("Tour not found");
        }

        Review review = ReviewMapper.fromDto(dto);
        AppUser appUser = (AppUser) userService.loadUserByUsername(username);
        review.setAuthor(appUser.getFirstName() + " " + appUser.getLastName());
        review.setTour(tourRepository.findById(dto.tourId()).orElseThrow(() -> new NotFoundException("Tour not found")));
        Image tempImage = null;
        if (image != null) {
            tempImage = imageService.processImage(image);
        }
        appUser.setProfilePicture(tempImage);
        review.setProfilePicture(tempImage);

        reviewRepository.save(review);
    }

    public Page<ReviewDto> getReviewsByTourId(Long tourId, int page, int size) {
        if (!tourRepository.existsById(tourId)) {
            throw new NotFoundException("Tour not found");
        }
        return reviewRepository.findAllByTour_Id(tourId, PageRequest.of(page, size)).map(ReviewMapper::toDto);
    }
}
