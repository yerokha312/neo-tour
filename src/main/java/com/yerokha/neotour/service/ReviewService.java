package com.yerokha.neotour.service;

import com.yerokha.neotour.dto.CreateReviewDto;
import com.yerokha.neotour.dto.ReviewDto;
import com.yerokha.neotour.entity.AppUser;
import com.yerokha.neotour.entity.Review;
import com.yerokha.neotour.exception.NotFoundException;
import com.yerokha.neotour.repository.ReviewRepository;
import com.yerokha.neotour.repository.TourRepository;
import com.yerokha.neotour.util.ReviewMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final TourRepository tourRepository;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final TourService tourService;


    public ReviewService(ReviewRepository reviewRepository, TourRepository tourRepository, UserDetailsServiceImpl userDetailsServiceImpl, TourService tourService) {
        this.reviewRepository = reviewRepository;
        this.tourRepository = tourRepository;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.tourService = tourService;
    }

    public ReviewDto addReview(CreateReviewDto dto, String username) {
        if (!tourRepository.existsById(dto.tourId())) {
            throw new NotFoundException("Tour not found");
        }

        Review review = ReviewMapper.fromDto(dto);
        AppUser appUser = (AppUser) userDetailsServiceImpl.loadUserByUsername(username);
        review.setAuthor(appUser.getFirstName() + " " + appUser.getLastName());
        review.setTour(tourRepository.findById(dto.tourId()).orElseThrow(() -> new NotFoundException("Tour not found")));
        review.setProfilePicture(appUser.getProfilePicture());

        ReviewDto reviewDto = ReviewMapper.toDto(reviewRepository.save(review));
        tourService.evictTourCacheById(dto.tourId());
        return reviewDto;
    }

    public Page<ReviewDto> getReviewsByTourId(Long tourId, int page, int size) {
        if (!tourRepository.existsById(tourId)) {
            throw new NotFoundException("Tour not found");
        }
        return reviewRepository.findAllByTour_Id(tourId, PageRequest.of(page, size)).map(ReviewMapper::toDto);
    }
}
