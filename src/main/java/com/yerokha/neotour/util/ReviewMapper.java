package com.yerokha.neotour.util;

import com.yerokha.neotour.dto.CreateReviewDto;
import com.yerokha.neotour.dto.ReviewDto;
import com.yerokha.neotour.entity.Review;

import java.time.LocalDateTime;

public class ReviewMapper {

    public static ReviewDto toDto(Review review) {
        return new ReviewDto(
                review.getAuthor(),
                review.getProfilePicture() != null ? review.getProfilePicture().getImageUrl() : null,
                review.getReviewDateTime(),
                review.getBody()
        );
    }

    public static Review fromDto(CreateReviewDto dto) {
        Review review = new Review();
        review.setReviewDateTime(LocalDateTime.now());
        review.setBody(dto.body());

        return review;
    }
}
